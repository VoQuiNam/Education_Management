package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.joctopus.model.Classes;
import com.joctopus.model.Contacts;
import com.joctopus.util.HibernateUtil;

public class ContactDaoImpl implements ContactDao{

	
	@SuppressWarnings("deprecation")
	@Override
	public void postContact(Contacts contact) throws SQLException {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(contact);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contacts> selectAllContact() {

		Transaction transaction = null;
		List<Contacts> contacts = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			contacts = session.createQuery("from com.joctopus.model.Contacts").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return contacts;
	}
	
	
}
