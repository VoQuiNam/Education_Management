package com.joctopus.dao;

import java.util.List;

import com.joctopus.model.Notification;
import com.joctopus.model.Notification_clients;

public interface NotificationAdminDao {
	void insertNotification(Notification_clients notification);
    
    List<Notification_clients> selectAllNotifications();
    
    void markAsRead(int notificationId);
    
    void removeNotification(int notificationId);
}
