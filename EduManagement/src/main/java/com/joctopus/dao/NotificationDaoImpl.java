package com.joctopus.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import com.joctopus.model.Notification;
import com.joctopus.model.User;
import com.joctopus.util.HibernateUtil;


public class NotificationDaoImpl implements NotificationDao {

    @PersistenceContext
    private EntityManager entityManager;

    private SessionFactory sessionFactory;

    public NotificationDaoImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void insertNotification(Notification notification) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(notification);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Notification> selectAllNotifications() {
        Session session = sessionFactory.openSession();
        List<Notification> notifications = null;
        try {
            notifications = session.createQuery("FROM Notification", Notification.class).list();
        } finally {
            session.close();
        }
        return notifications;
    }

    @Override
    public void markAsRead(int notificationId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Notification notification = session.get(Notification.class, notificationId);
            if (notification != null) {
                notification.setRead(true);
                session.update(notification);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public void removeNotification(int notificationId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Notification notification = session.get(Notification.class, notificationId);
                if (notification != null) {
                    session.delete(notification);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace(); // Replace with a logger
            }
        }
    }
    
   
 
}
