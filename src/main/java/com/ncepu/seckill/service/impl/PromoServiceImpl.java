package com.ncepu.seckill.service.impl;

import com.ncepu.seckill.dao.PromoDOMapper;
import com.ncepu.seckill.dataobject.PromoDO;
import com.ncepu.seckill.service.PromoService;
import com.ncepu.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        // 获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        // dataobject -> model
        PromoModel promoModel = convertFromDataObject(promoDO);
        if (promoModel == null) {
            return null;
        }
        // 判断当前时间是否秒杀活动状态
        DateTime now = new DateTime();
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1); // 尚未开始
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3); // 已经结束
        } else {
            promoModel.setStatus(2); // 进行中
        }

        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO) {
        if (promoDO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
