package com.codenation.ecommerce.models;

import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Ferooz on 10/07/16.
 */
@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;
    @Column(name = "User_ID")
    private int userId;
    @Column(name = "Email_ID")
    private String email;
    @Column(name = "Name")
    private String name;
    @Column(name = "PhoneNumber")
    private String phone;
    @Column(name = "Description")
    private String description;
    @Column(name = "FeedbackTime")
    private Timestamp timestamp;
    @Column(name = "UpdateTime")
    private Timestamp updateTime;

    public Feedback() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
