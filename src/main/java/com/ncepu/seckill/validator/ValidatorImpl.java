package com.ncepu.seckill.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    // 实现校验方法并返回校验结果
    public ValidationResult validate(Object bean) {
        final ValidationResult result = new ValidationResult();
        // 使用validator.validate校验bean，若有违背validator的规则，则将其加入到constraintValidatorSet中
        Set<ConstraintViolation<Object>> constraintValidatorSet = validator.validate(bean);
        if (constraintValidatorSet.size() > 0) {
            // 说明有错误
            result.setHasErrors(true);
            constraintValidatorSet.forEach(constraintValidation -> {
                String errMsg = constraintValidation.getMessage();
                String propertyName = constraintValidation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 将hibernate validator通过工厂初始化方法使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
