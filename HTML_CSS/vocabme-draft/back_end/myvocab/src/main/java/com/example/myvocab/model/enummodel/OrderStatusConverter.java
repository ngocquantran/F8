package com.example.myvocab.model.enummodel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        if (orderStatus==null){
            return null;
        }
        return orderStatus.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer integer) {
        if (integer==null){
            return null;
        }
        return Stream.of(OrderStatus.values()).filter(orderStatus -> orderStatus.getCode()==integer.intValue()).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
