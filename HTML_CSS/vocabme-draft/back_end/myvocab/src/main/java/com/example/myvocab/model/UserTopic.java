package com.example.myvocab.model;

import com.example.myvocab.model.enummodel.TopicState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_topic")
public class UserTopic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private TopicState status;

    @ManyToOne
    @JoinColumn(name = "id_user_course", referencedColumnName = "id", nullable = false)
    private UserCourse userCourse;

    @ManyToOne
    @JoinColumn(name = "id_topic", referencedColumnName = "id", nullable = false)
    private Topic topic;

}
