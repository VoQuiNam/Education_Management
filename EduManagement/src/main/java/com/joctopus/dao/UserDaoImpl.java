package com.joctopus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.joctopus.model.Ucl;
import com.joctopus.model.User;
import com.joctopus.model.Classes;
import com.joctopus.util.HibernateUtil;

public class UserDaoImpl implements UserDao{
	private Connection connection;
	public UserDaoImpl() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void insertUser(User user) throws SQLException {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(user);
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
	public User selectUser(int id) {
		Transaction transaction = null;
		User user = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			user = session.get(com.joctopus.model.User.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return user;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> selectAllUsers() {

		Transaction transaction = null;
		List<User> users = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			users = session.createQuery("from com.joctopus.model.User").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return users;
	}
	
	@Override
	public void deleteUser(int id) throws SQLException{
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bắt đầu một transaction
            transaction = session.beginTransaction();
            
         // Delete a todo object
			User user = session.get(com.joctopus.model.User.class, id);

            // Xóa người dùng
            session.delete(user);

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
	public void updateUser(User user) throws SQLException {
		
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
	public boolean isAccountExists(String account) throws SQLException {
	    Transaction transaction = null;
	    final boolean[] exists = {false}; // Sử dụng một mảng chứa giá trị để thỏa mãn yêu cầu final/effectively final
	    // được khai báo để lưu trữ kết quả kiểm tra tồn tại của tài khoản
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Bắt đầu một transaction
	        transaction = session.beginTransaction();

	        // Thực hiện truy vấn SQL để kiểm tra xem tài khoản có tồn tại không
	        session.doWork(connection -> {
	            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE account = ?")) {
	                statement.setString(1, account);
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
	public boolean isPhoneNumberExists(String phoneNumber) throws SQLException {
	    Transaction transaction = null;
	    final boolean[] exists = {false}; // Sử dụng một mảng chứa giá trị để thỏa mãn yêu cầu final/effectively final
	    // được khai báo để lưu trữ kết quả kiểm tra tồn tại của tài khoản
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Bắt đầu một transaction
	        transaction = session.beginTransaction();

	        // Thực hiện truy vấn SQL để kiểm tra xem tài khoản có tồn tại không
	        session.doWork(connection -> {
	            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE phone_number = ?")) {
	                statement.setString(1, phoneNumber);
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
	public User getUserByAccount(String account) {
	    Transaction transaction = null;
	    User user = null;
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    try  {
	        // start a transaction
	        transaction = session.beginTransaction();
	        
	        // Query to retrieve user by account username
	        Query query = session.createQuery("FROM User WHERE account = :account");
	        query.setParameter("account", account);
	        
	        // Execute query and get the user
	        user = (User) query.uniqueResult();
	        
	        // commit transaction
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return user;
	}
	
	@Override
	public List<User> selectUsersByType(String type) {
	    Transaction transaction = null;
	    List<User> users = null;
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    try {
	        // start a transaction
	        transaction = session.beginTransaction();
	        
	        // Query to retrieve users by type
	        Query query = session.createQuery("FROM User WHERE type = :type");
	        query.setParameter("type", type);
	        
	        // Execute query and get the users
	        users = query.list();
	        
	        // commit transaction
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return users;
	}
	
	// Implementation of insertUcl method
    @Override
    public void insertUcl(Ucl ucl) throws SQLException {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // save the Ucl object
            session.save(ucl);
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
    public boolean isUserRegisteredForClass(User user, Classes classes) throws SQLException {
        Transaction transaction = null;
        boolean isRegistered = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();

            // Example query assuming Ucl is the entity linking User and Classes
            Query query = session.createQuery("SELECT COUNT(*) FROM Ucl WHERE id_user = :user AND class_id = :classes");
            query.setParameter("user", user);
            query.setParameter("classes", classes);

            long count = (long) query.uniqueResult();
            isRegistered = count > 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isRegistered;
    }
	
	
}
