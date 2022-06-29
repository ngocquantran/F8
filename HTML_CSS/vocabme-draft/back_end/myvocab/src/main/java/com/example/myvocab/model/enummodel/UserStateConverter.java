package com.example.myvocab.model.enummodel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserStateConverter implements AttributeConverter<UserState,Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserState userState) {
        if (userState==null){
            return null;
        }
        return userState.getCode();
    }

    @Override
    public UserState convertToEntityAttribute(Integer integer) {
        if (integer==null){
            return null;
        }
        return Stream.of(UserState.values()).filter(u -> u.getCode()==integer.intValue()).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
