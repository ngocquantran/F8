package com.example.myvocab.dto;

public interface TopicToCourseDto {
    Long getId();

    String getTitle();

    CourseInfo getCourse();

    interface CourseInfo {
        Long getId();
    }
}
