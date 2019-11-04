package com.findme.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.findme.utils.LocalDateDeserializer;
import com.findme.utils.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "POST")
public class Post {


    private Long id;
    private String message;
    private LocalDate datePosted;
    private User userPosted;
    //TODO
    //levels permissions;
    //TODO
    //comments;

    public Post() {
    }

    public Post(Long id, String message, LocalDate datePosted, User userPosted) {
        this.id = id;
        this.message = message;
        this.datePosted = datePosted;
        this.userPosted = userPosted;
    }

    public Post(String message, LocalDate datePosted, User userPosted) {
        this.message = message;
        this.datePosted = datePosted;
        this.userPosted = userPosted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }

    @Id
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column (name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    @Column(name = "DATE_POSTED")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getDatePosted() {
        return datePosted;
    }

    @ManyToOne
    @JoinColumn(name = "USER_POSTED_ID")
    public User getUserPosted() {
        return userPosted;
    }
}
