package com.ncepu.seckill.service;

import com.ncepu.seckill.error.BusinessException;
import com.ncepu.seckill.service.model.UserModel;

public interface UserService {

    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    UserModel validateLogin(String telphone, String password) throws BusinessException;
}
