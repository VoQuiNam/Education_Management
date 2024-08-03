package com.joctopus.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.joctopus.model.Notification;
import com.joctopus.model.User;
import com.joctopus.util.HibernateUtil;

public interface NotificationDao {
    void insertNotification(Notification notification);
    
    List<Notification> selectAllNotifications();
    
    void markAsRead(int notificationId);
    
    void removeNotification(int notificationId);
    

 
}