package com.findme.utils;

import com.findme.dao.UserDaoInterface;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;


public class UserValidator implements Validator {

    @Autowired
    UserDaoInterface userDaoInterface;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user =(User)o;
         if (userDaoInterface.getOne(user.getPhone()) != null){
             errors.rejectValue("phone", "BAD_REQUEST", "This phone is already used");
         }
    }
}
