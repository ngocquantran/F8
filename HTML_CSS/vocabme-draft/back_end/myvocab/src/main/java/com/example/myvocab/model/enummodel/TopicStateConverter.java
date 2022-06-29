package com.example.myvocab.model.enummodel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TopicStateConverter implements AttributeConverter<TopicState,Integer> {
    @Override
    public Integer convertToDatabaseColumn(TopicState topicState) {
        if (topicState==null){
            return null;
        }
        return topicState.getCode();
    }

    @Override
    public TopicState convertToEntityAttribute(Integer integer) {
        if (integer==null){
            return null;
        }
        return Stream.of(TopicState.values()).filter(u -> u.getCode()==integer.intValue()).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
