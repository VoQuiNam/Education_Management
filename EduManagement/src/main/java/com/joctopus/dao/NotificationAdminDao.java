package com.joctopus.dao;

import java.util.List;

import com.joctopus.model.Notification;

public interface NotificationAdminDao {
void insertNotification(Notification notification);
    
    List<Notification> selectAllNotifications();
    
    void markAsRead(int notificationId);
    
    void removeNotification(int notificationId);
}
