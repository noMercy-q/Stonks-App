package com.example.stonks.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import com.example.stonks.model.User;
import com.example.stonks.service.UserService;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        
        if (user.getUsername().length() < 5 || user.getUsername().length() > 20) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("usernaem", "Duplicate.userFrom.username");
        }

        ValidationUtils.rejectIfEmpty(errors, "password", "Required");
        
        if (user.getPassword().length() < 4) {
            errors.rejectValue("password", "Size.userFrom.password");
        }
    }
}
