package com.groupware.employee.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
* PasswordValidator
* パスワード相関チェック実行クラス
* パスワードと確認用が一致するか検証する
* 
* @author　A.Watanabe
* @version　1.0.0
*/

public class PasswordValidator implements ConstraintValidator<PasswordCheck, Object> {

    private String field1;
    private String field2;
    private String message;

    @Override
    public void initialize(PasswordCheck annotation) {
        this.field1 = annotation.field1();
        this.field2 = annotation.field2();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // フォームから値を取り出す
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object value1 = beanWrapper.getPropertyValue(field1); // 新しいパスワード
        Object value2 = beanWrapper.getPropertyValue(field2); // 確認用

        // どちらかが空ならチェックしない
        if (value1 == null || value2 == null) {
            return true;
        }

        // 一致しているか判定
        boolean isMatched = value1.equals(value2);
        
        if (isMatched) {
            return true;
        } else {

        	  context.disableDefaultConstraintViolation();
              context.buildConstraintViolationWithTemplate(message)
                     .addPropertyNode(field2) 
                     .addConstraintViolation();
            
            return false;
        }
    }
}
