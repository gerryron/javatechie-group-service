package com.yhoboo.group.entity;

import javax.persistence.*;

@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue
    private int postId;
    private String subject;
    private String description;
    private String userName;
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }
}
