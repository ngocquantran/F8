package com.example.myvocab.model;

import com.example.myvocab.model.enummodel.LearningStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_topic_sentence")
public class UserTopicSentence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LearningStage learningStage;
    private int testTime;

    @ManyToOne
    @JoinColumn(name = "id_user_topic", referencedColumnName = "id", nullable = false)
    private UserTopic userTopic;



}
