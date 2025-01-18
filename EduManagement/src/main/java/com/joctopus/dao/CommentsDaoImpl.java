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
import com.joctopus.model.User;
import com.joctopus.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CommentsDaoImpl implements CommentsDao{
	private Connection connection;
	public CommentsDaoImpl() {
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
}
