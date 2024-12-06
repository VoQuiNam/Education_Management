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
import com.joctopus.model.User;
import com.joctopus.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class BannerDaoImpl implements BannerDao{
	private Connection connection;
	public BannerDaoImpl() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void insertBanners(Banner banner) throws SQLException {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(banner);
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
	public void updateBanner(Banner banner) throws SQLException {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.saveOrUpdate(banner);
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
	public Banner selectBanners(int id) {
		Transaction transaction = null;
		Banner banner = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			banner = session.get(com.joctopus.model.Banner.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return banner;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Banner> selectAllBanners() {

		Transaction transaction = null;
		List<Banner> banner = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try  {
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object
			
			banner = session.createQuery("from com.joctopus.model.Banner").getResultList();
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return banner;
	} 
	
	@Override
	public void deleteBanner(int id) throws SQLException{
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bắt đầu một transaction
            transaction = session.beginTransaction();
            
         // Delete a todo object
			Banner banner = session.get(com.joctopus.model.Banner.class, id);

            // Xóa người dùng
            session.delete(banner);

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
