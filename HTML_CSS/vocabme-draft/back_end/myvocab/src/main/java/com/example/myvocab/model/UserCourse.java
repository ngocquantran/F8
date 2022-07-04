package com.example.myvocab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Formula;

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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course")
    @JsonIgnore
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private Users user;

    @Formula("(SELECT COUNT(*)\n" +
            "FROM user_topic ut \n" +
            "INNER JOIN user_course uc ON ut.id_user_course =uc.id \n" +
            "WHERE ut.status ='PASS' AND uc.id=id)")
    private Integer finishedTopics;

    @Formula("(SELECT COUNT(*) \n" +
            "FROM user_topic_vocab utv \n" +
            "INNER JOIN user_topic ut ON utv.id_user_topic =ut.id \n" +
            "INNER JOIN user_course uc ON ut.id_user_course =uc.id \n" +
            "WHERE ut.status ='PASS' AND utv.status =1 AND utv.learningStage ='NOW' AND uc.id=id)")
    private Integer passedElement;

    @Formula("(SELECT COUNT(*) \n" +
            "FROM user_topic_vocab utv \n" +
            "INNER JOIN user_topic ut ON utv.id_user_topic =ut.id \n" +
            "INNER JOIN user_course uc ON ut.id_user_course =uc.id \n" +
            "WHERE ut.status ='PASS' AND utv.status =0 AND utv.learningStage ='NOW' AND uc.id=id)")
    private Integer failedElement;



}
