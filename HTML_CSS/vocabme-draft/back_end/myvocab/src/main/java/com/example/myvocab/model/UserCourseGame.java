package com.example.myvocab.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_course_game")
public class UserCourseGame {
    private int id;
    private int testTime;
    private UserCourse userCourseByIdUserCourse;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "testTime", nullable = false)
    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourseGame that = (UserCourseGame) o;
        return id == that.id && testTime == that.testTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testTime);
    }

    @ManyToOne
    @JoinColumn(name = "id_user_course", referencedColumnName = "id", nullable = false)
    public UserCourse getUserCourseByIdUserCourse() {
        return userCourseByIdUserCourse;
    }

    public void setUserCourseByIdUserCourse(UserCourse userCourseByIdUserCourse) {
        this.userCourseByIdUserCourse = userCourseByIdUserCourse;
    }
}
