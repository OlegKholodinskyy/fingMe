package com.findme.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.findme.utils.LocalDateDeserializer;
import com.findme.utils.LocalDateSerializer;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER_TABLE")
public class User {

    private Long id;
    @NotBlank(message = "User name is required")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Phone is required")
    private String phone;
    // TODO from existed data
    private String country;
    private String city;

    private Integer age;
    private Date dateRegistered;
    private Date dateLastActive;
    //TODO enum
    private String relationshipStatus;
    private String religion;
    // TODO from existed data
    private String school;
    private String university;

    private List<Message> messagesSent;
    private List<Message> messagesReceived;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String phone, String country, String city, Integer age, Date dateRegistered, Date dateLastActive, String relationshipStatus, String religion, String school, String university, List<Message> messagesSent, List<Message> messagesReceived) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.age = age;
        this.dateRegistered = dateRegistered;
        this.dateLastActive = dateLastActive;
        this.relationshipStatus = relationshipStatus;
        this.religion = religion;
        this.school = school;
        this.university = university;
        this.messagesSent = messagesSent;
        this.messagesReceived = messagesReceived;
    }

    //    private String [] interests;

    @Id
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    @Column(name = "AGE")
    public Integer getAge() {
        return age;
    }

    @Column(name = "DATE_REGISTERED")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public Date getDateRegistered() {
        return dateRegistered;
    }

    @Column(name = "DATE_LAST_ACTIVE")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public Date getDateLastActive() {
        return dateLastActive;
    }

    @Column(name = "RELATIONSHIPS_STATUS")
    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    @Column(name = "RELIGION")
    public String getReligion() {
        return religion;
    }

    @Column(name = "SCHOOL")
    public String getSchool() {
        return school;
    }

    @Column(name = "UNIVERSITY")
    public String getUniversity() {
        return university;
    }

    @OneToMany(mappedBy = "userFrom")
    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    @OneToMany(mappedBy = "userTo")
    public List<Message> getMessagesReceived() {
        return messagesReceived;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setDateLastActive(Date dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setMessagesSent(List<Message> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public void setMessagesReceived(List<Message> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
