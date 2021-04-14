package com.ncepu.seckill.service;

import com.ncepu.seckill.error.BusinessException;
import com.ncepu.seckill.service.model.OrderModel;

public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
