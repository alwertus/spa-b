package com.tretsoft.spa.helpers;

import com.tretsoft.spa.exception.BadRequestException;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.List;

@Log4j2
public class ObjectOperations {

    public static void copyNonNullFields(Object from, Object to, List<String> ignoreAttributes) {
        // Get the list of all fields for the class of 'from'
        Field[] fields = from.getClass().getDeclaredFields();

        // Loop through all the fields
        for (Field field : fields) {
            if (ignoreAttributes.contains(field.getName()))
                continue;

            // Make the field accessible so we can read its value
            field.setAccessible(true);

            try {
                // Get the value of the field in 'from'
                Object value = field.get(from);

                // If the value is not null, set the same field in 'to' to the same value
                if (value != null) {
                    field.set(to, value);
                }
            } catch (IllegalAccessException ex) {
                log.error(ex);
                throw new BadRequestException("Cannot copy object attributes");
            }
        }
    }

    public static void copyNonNullFields(Object from, Object to) {
        copyNonNullFields(from, to, null);
    }

}
