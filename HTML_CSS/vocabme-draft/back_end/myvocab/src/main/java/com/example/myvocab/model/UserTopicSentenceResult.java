package com.example.myvocab.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_topic_sentence_result")
public class UserTopicSentenceResult {
    private long id;
    private String questionTilte;
    private String questionContent;
    private String answer;
    private String userAnswer;
    private byte status;
    private UserTopic userTopicByIdUserTopic;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "questionTilte", nullable = false, length = 100)
    public String getQuestionTilte() {
        return questionTilte;
    }

    public void setQuestionTilte(String questionTilte) {
        this.questionTilte = questionTilte;
    }

    @Basic
    @Column(name = "questionContent", nullable = true, length = 100)
    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    @Basic
    @Column(name = "answer", nullable = false, length = 100)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "userAnswer", nullable = false, length = 150)
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTopicSentenceResult that = (UserTopicSentenceResult) o;
        return id == that.id && status == that.status && Objects.equals(questionTilte, that.questionTilte) && Objects.equals(questionContent, that.questionContent) && Objects.equals(answer, that.answer) && Objects.equals(userAnswer, that.userAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionTilte, questionContent, answer, userAnswer, status);
    }

    @ManyToOne
    @JoinColumn(name = "id_user_topic", referencedColumnName = "id", nullable = false)
    public UserTopic getUserTopicByIdUserTopic() {
        return userTopicByIdUserTopic;
    }

    public void setUserTopicByIdUserTopic(UserTopic userTopicByIdUserTopic) {
        this.userTopicByIdUserTopic = userTopicByIdUserTopic;
    }
}
