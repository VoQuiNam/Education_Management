package com.joctopus.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private boolean is_read;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    public Notification() {
        // Default constructor
    }

    public Notification(String message, boolean isRead, User userId) {
        this.message = message;
        this.is_read = isRead;
        this.userId = userId;
    }

    // Getters and setters
    // Omitted for brevity, but ensure you have getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return is_read;
    }

    public void setRead(boolean read) {
    	is_read = read;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
