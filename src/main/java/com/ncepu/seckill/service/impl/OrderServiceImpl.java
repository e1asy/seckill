package com.ncepu.seckill.service.impl;

import com.ncepu.seckill.dao.OrderDOMapper;
import com.ncepu.seckill.dao.SequenceDOMapper;
import com.ncepu.seckill.dataobject.OrderDO;
import com.ncepu.seckill.dataobject.SequenceDO;
import com.ncepu.seckill.error.BusinessException;
import com.ncepu.seckill.error.EmBusinessError;
import com.ncepu.seckill.service.ItemService;
import com.ncepu.seckill.service.OrderService;
import com.ncepu.seckill.service.UserService;
import com.ncepu.seckill.service.model.ItemModel;
import com.ncepu.seckill.service.model.OrderModel;
import com.ncepu.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {

        // 1.校验下单状态，下单商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }

        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不正确");
        }
        // 2.落单减库存 或 支付减库存
        // 1）落单减库存：在调用createOrder前将某一库存信息锁（关键字：锁）定给该用户使用（存在恶意下单不付钱的情况）
        // 2）支付减库存：在createOrder时查看余量，大于0，即可以供其下单，直到支付宝/微信完成支付后，再减库存（无法保证超卖情况）
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        // 3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        // 订单号在数据库中设计的不是自增，它的生成是有特定规则的
        orderModel.setId(generateOrderNo()); // 生成交易流水号
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        // 附加.加上商品的销量
        itemService.increaseSales(itemId, amount);

        // 4.返回前端
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo() {
        // 订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        // 前8位为时间信息，年月日 20210414
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        // 中间6位为自增序列
        // 获取当前sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequencestr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequencestr.length(); i ++ ) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequencestr);

        // 最后2位为分库分表位（暂时写死）
        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO); // 注意价格Double与BigDecimal不兼容
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}
