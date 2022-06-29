package com.example.myvocab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "topic")
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String img;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Course course;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "vocab_topic",
            joinColumns = @JoinColumn(name = "id_topic"),
            inverseJoinColumns = @JoinColumn(name = "id_vocab")
    )
    private Set<Vocab> vocabs=new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "sentence_topic",
            joinColumns = @JoinColumn(name = "id_topic"),
            inverseJoinColumns = @JoinColumn(name = "id_sentence")
    )
    private Set<Sentence> sentences=new HashSet<>();

}
