package com.tretsoft.spa.validator;

import com.tretsoft.spa.exception.NullAttributeException;
import com.tretsoft.spa.model.dto.SpaUserDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.LinkedList;
import java.util.List;


@Component
public class SpaUserValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return SpaUserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        SpaUserDto dto = (SpaUserDto) o;
        List<String> nullAttrs = new LinkedList<>();

        if (dto.getLogin() == null) nullAttrs.add("login");
        if (dto.getPassword() == null) nullAttrs.add("password");

        if (nullAttrs.size() > 0)
            throw new NullAttributeException(nullAttrs.toArray());

    }
}
