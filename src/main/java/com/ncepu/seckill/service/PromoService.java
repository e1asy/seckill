package com.ncepu.seckill.service;

import com.ncepu.seckill.service.model.PromoModel;

public interface PromoService {

    //根据itemid获取即将进行或者正在进行的活动信息
    PromoModel getPromoByItemId(Integer itemId);
}
