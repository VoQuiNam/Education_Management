package com.joctopus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.joctopus.model.Classes;
import com.joctopus.model.Ucl;
import com.joctopus.model.User;
import com.joctopus.util.HibernateUtil;

public class ClassesDaoImpl implements ClassesDao{
	private Connection connection;
	public ClassesDaoImpl() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void insertClasses(Classes classes) throws SQLException {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(classes);
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
	public Classes selectClasses(int id) {
		Transaction transaction = null;
		Classes classes = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			classes = session.get(com.joctopus.model.Classes.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return classes;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Classes> selectAllClasses() {

		Transaction transaction = null;
		List<Classes> classes = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			classes = session.createQuery("from com.joctopus.model.Classes").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return classes;
	}
	
	@Override
	public void deleteClasses(int id) throws SQLException{
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bắt đầu một transaction
            transaction = session.beginTransaction();
            
         // Delete a todo object
			Classes classes = session.get(com.joctopus.model.Classes.class, id);

            // Xóa người dùng
            session.delete(classes);

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
	public void updateClasses(Classes user) throws SQLException {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.saveOrUpdate(user);
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
	public boolean isClassNameExists(String className) throws SQLException {
	    Transaction transaction = null;
	    final boolean[] exists = {false}; // Sử dụng một mảng chứa giá trị để thỏa mãn yêu cầu final/effectively final
	    // được khai báo để lưu trữ kết quả kiểm tra tồn tại của tài khoản
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Bắt đầu một transaction
	        transaction = session.beginTransaction();

	        // Thực hiện truy vấn SQL để kiểm tra xem tài khoản có tồn tại không
	        session.doWork(connection -> {
	            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM classes WHERE class_name = ?")) {
	                statement.setString(1, className);
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                    	//ResultSet được sử dụng để lấy kết quả của truy vấn và kiểm tra xem có bất kỳ dòng nào trả về không. 
	                    	//Nếu có, giá trị của biến exists[0] sẽ được thiết lập dựa trên kết quả truy vấn.
	                        exists[0] = resultSet.getInt(1) > 0;
	                    }
	                }
	            }
	        });

	        // Commit transaction
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    }
	    return exists[0];
	}
	
	 @Override
	    public Classes getClassById(int id) {
	        Transaction transaction = null;
	        Classes classes = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // get a Classes object
	            classes = session.get(Classes.class, id);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return classes;
	    }
	 
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Classes> selectClassesByStatus(String status) {
	     Transaction transaction = null;
	     List<Classes> classes = null;
	     Session session = HibernateUtil.getSessionFactory().openSession();
	     try {
	         transaction = session.beginTransaction();
	         if (status == null || status.isEmpty() || status.equalsIgnoreCase("All")) {
	             classes = session.createQuery("from Classes").getResultList();
	         } else {
	             classes = session.createQuery("from Classes where status = :status")
	                              .setParameter("status", status)
	                              .getResultList();
	         }
	         transaction.commit();
	     } catch (Exception e) {
	         if (transaction != null) {
	             transaction.rollback();
	         }
	         e.printStackTrace();
	     }
	     return classes;
	 }

}
