package com.example.myvocab.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "topic_discuss")
public class TopicDiscuss {
    private long id;
    private String content;
    private Date timeAt;
    private Long idParent;
    private Topic topicByIdTopic;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 300)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "timeAt", nullable = false)
    public Date getTimeAt() {
        return timeAt;
    }

    public void setTimeAt(Date timeAt) {
        this.timeAt = timeAt;
    }

    @Basic
    @Column(name = "id_parent", nullable = true)
    public Long getIdParent() {
        return idParent;
    }

    public void setIdParent(Long idParent) {
        this.idParent = idParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicDiscuss that = (TopicDiscuss) o;
        return id == that.id && Objects.equals(content, that.content) && Objects.equals(timeAt, that.timeAt) && Objects.equals(idParent, that.idParent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, timeAt, idParent);
    }

    @ManyToOne
    @JoinColumn(name = "id_topic", referencedColumnName = "id", nullable = false)
    public Topic getTopicByIdTopic() {
        return topicByIdTopic;
    }

    public void setTopicByIdTopic(Topic topicByIdTopic) {
        this.topicByIdTopic = topicByIdTopic;
    }
}
