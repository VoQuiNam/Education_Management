package com.joctopus.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "notification_clients")
public class Notification_clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private boolean is_read;
    
    @Column(name = "type")
    private String type; // Thêm trường type

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    public Notification_clients() {
        // Default constructor
    }

    public Notification_clients(String message, boolean isRead, String type,  User userId) {
        this.message = message;
        this.is_read = isRead;
        this.type = type;
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
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
