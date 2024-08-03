package com.joctopus.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.joctopus.model.Classes;
import com.joctopus.model.Ucl;
import com.joctopus.model.UclId;
import com.joctopus.util.HibernateUtil;

public class ClassesUserDaoImpl implements ClassesUserDao{
	private Connection connection;
	public ClassesUserDaoImpl() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void insertClassUser(Ucl ucl) throws SQLException {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
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
	public Ucl selectUcl(int id) {
		Transaction transaction = null;
		Ucl ucl = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			ucl = session.get(com.joctopus.model.Ucl.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return ucl;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ucl> selectAllUcl() {

		Transaction transaction = null;
		List<Ucl> ucl = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			ucl = session.createQuery("from com.joctopus.model.Ucl").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return ucl;
	}
	
	@Override
	public void deleteUcl(int id_user, int class_id) throws SQLException{
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bắt đầu một transaction
            transaction = session.beginTransaction();
            
            // Create composite key
            UclId uclId = new UclId(id_user, class_id);
            
         // Delete a todo object
            Ucl ucl = session.get(Ucl.class, uclId);

            // Xóa người dùng
            if (ucl != null) {
                session.delete(ucl);
            }

            // Commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


}
