package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import com.joctopus.model.Classes;
import com.joctopus.model.Contacts;
import com.joctopus.model.User;

public interface ContactDao {
	void postContact(Contacts contact) throws SQLException;
	
	List<Contacts> selectAllContact();
}
