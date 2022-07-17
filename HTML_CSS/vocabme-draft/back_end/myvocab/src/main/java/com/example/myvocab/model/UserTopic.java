package com.example.myvocab.model;

import com.example.myvocab.model.enummodel.TopicState;
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
@Table(name = "user_topic")
public class UserTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TopicState status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user_course", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private UserCourse userCourse;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_topic", referencedColumnName = "id", nullable = false)
    private Topic topic;

    @Formula("(SELECT COUNT(*) \n" +
            "FROM user_topic_vocab utv \n" +
            "INNER JOIN user_topic ut ON utv.id_user_topic =ut.id \n" +
            "WHERE (ut.status ='PASS' OR ut.status ='CONTINUE') AND utv.status =1 AND ut.id = id)")
    private Integer passedElement;


    @Formula("(SELECT COUNT(*) \n" +
            "FROM vocab_topic vt \n" +
            "INNER JOIN topic t ON vt.id_topic =t.id \n" +
            "INNER JOIN user_topic ut ON ut.id_topic =t.id \n" +
            "WHERE ut.id = id)")
    private Integer totalElement;


}
