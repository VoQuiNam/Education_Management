package com.joctopus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.joctopus.model.Banner;
import com.joctopus.model.Classes;
import com.joctopus.model.Comments;
import com.joctopus.model.Ucl;
import com.joctopus.model.User;
import com.joctopus.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CommentAdminssionDaoImpl implements CommentAdminssionDao{
	private Connection connection;
	public CommentAdminssionDaoImpl() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comments> selectAllComments() {

		Transaction transaction = null;
		List<Comments> comments = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			comments = session.createQuery("from com.joctopus.model.Comments").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return comments;
	}
	
	@Override
	public Classes getClassDetails(int classId) {
	    Transaction transaction = null;
	    Classes classDetails = null;
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    try {
	        // Start a transaction
	        transaction = session.beginTransaction();
	        
	        // Query to get class details by classId
	        classDetails = session.get(Classes.class, classId); // Sử dụng session.get
	        
	        // Commit transaction
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    }
	    return classDetails; // Trả về chi tiết lớp học
	}
	
	@Override
	public List<Comments> selectCommentsByClassId(int class_id) {
	    Transaction transaction = null;
	    List<Comments> comments = null;
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    try {
	        // Start a transaction
	        transaction = session.beginTransaction();

	        // Query to get comments by classId
	        comments = session.createQuery("from Comments c where c.class_id.id = :class_id", Comments.class)
	                          .setParameter("class_id", class_id)
	                          .getResultList();

	        // Commit transaction
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close(); // Ensure the session is closed
	    }
	    return comments; // Return list of comments
	}
	
	@Override
	public void postComments(Comments comments) throws SQLException {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(comments);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteComments(int id) throws SQLException{
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bắt đầu một transaction
            transaction = session.beginTransaction();
            
         // Delete a todo object
			Comments comments = session.get(com.joctopus.model.Comments.class, id);

            // Xóa người dùng
            session.delete(comments);

            // Commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

	
	@Override
	public Comments selectComments(int id) {
		Transaction transaction = null;
		Comments comments = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			comments = session.get(com.joctopus.model.Comments.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return comments;
	}
	
	@Override
	public void updateComments(Comments comments) throws SQLException {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.saveOrUpdate(comments);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}



	 
}
