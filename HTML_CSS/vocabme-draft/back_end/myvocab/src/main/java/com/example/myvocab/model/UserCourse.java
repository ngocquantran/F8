package com.example.myvocab.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_course")
public class UserCourse {
    private long id;
    private Integer finishedTopics;
    private Integer passedElement;
    private Integer failedElement;
    private Course courseByIdCourse;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "finishedTopics", nullable = true)
    public Integer getFinishedTopics() {
        return finishedTopics;
    }

    public void setFinishedTopics(Integer finishedTopics) {
        this.finishedTopics = finishedTopics;
    }

    @Basic
    @Column(name = "passedElement", nullable = true)
    public Integer getPassedElement() {
        return passedElement;
    }

    public void setPassedElement(Integer passedElement) {
        this.passedElement = passedElement;
    }

    @Basic
    @Column(name = "failedElement", nullable = true)
    public Integer getFailedElement() {
        return failedElement;
    }

    public void setFailedElement(Integer failedElement) {
        this.failedElement = failedElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourse that = (UserCourse) o;
        return id == that.id && Objects.equals(finishedTopics, that.finishedTopics) && Objects.equals(passedElement, that.passedElement) && Objects.equals(failedElement, that.failedElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, finishedTopics, passedElement, failedElement);
    }

    @ManyToOne
    @JoinColumn(name = "id_course", referencedColumnName = "id", nullable = false)
    public Course getCourseByIdCourse() {
        return courseByIdCourse;
    }

    public void setCourseByIdCourse(Course courseByIdCourse) {
        this.courseByIdCourse = courseByIdCourse;
    }
}
