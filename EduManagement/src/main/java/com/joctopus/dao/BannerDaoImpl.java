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
	    try {
	        // Start a transaction
	        transaction = session.beginTransaction();
	        
	        // Query banners sorted by playOrder
	        banner = session.createQuery("FROM com.joctopus.model.Banner ORDER BY playOrder ASC").getResultList();
	        
	        // Commit transaction
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
	public void deleteBanner(int id) throws SQLException {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        // Bắt đầu transaction
	        transaction = session.beginTransaction();
	        
	        // Lấy banner cần xóa
	        Banner banner = session.get(Banner.class, id);
	        if (banner == null) {
	            throw new IllegalArgumentException("Banner with ID " + id + " not found.");
	        }

	        int playOrder = banner.getPlayOrder();
	        String position = banner.getPosition();

	        // Xóa banner
	        session.delete(banner);

	        // Cập nhật lại PlayOrder cho các banner khác trong cùng position
	        session.createQuery("UPDATE Banner b SET b.PlayOrder = b.PlayOrder - 1 " +
	                            "WHERE b.Position = :position AND b.PlayOrder > :playOrder")
	               .setParameter("position", position)
	               .setParameter("playOrder", playOrder)
	               .executeUpdate();

	        // Commit transaction
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	        throw new SQLException("Error while deleting banner and updating PlayOrder.", e);
	    }
	}

	
	@Override
	public int getMaxPlayOrderByPosition(String position) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	    	//Sử dụng hàm COALESCE để trả về 0 nếu không có giá trị nào (tránh lỗi null).
	        Integer maxPlayOrder = (Integer) session
	            .createQuery("SELECT COALESCE(MAX(b.PlayOrder), 0) FROM Banner b WHERE b.Position = :position")
	            .setParameter("position", position)
	            .uniqueResult();
	        System.out.println("Max PlayOrder fetched for position " + position + ": " + maxPlayOrder);
	        return maxPlayOrder != null ? maxPlayOrder : 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0; // Default value in case of error
	    }
	}

	
	@Override
	public void updatePlayOrder(int id, int playOrder) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        Query query = session.createQuery("UPDATE Banner SET PlayOrder = :playOrder WHERE id = :id");
	        query.setParameter("playOrder", playOrder);
	        query.setParameter("id", id);
	        query.executeUpdate();
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    }
	}




}
