package com.biathlon84.forum.model.validator;

import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findByUsername(email);
        return user == null;
    }
}
