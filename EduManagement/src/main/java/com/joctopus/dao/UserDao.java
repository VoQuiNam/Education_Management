package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import com.joctopus.model.Classes;
import com.joctopus.model.Ucl;
import com.joctopus.model.User;

public interface UserDao {
	void insertUser(User user) throws SQLException;

	User selectUser(int Id);

	 List<User> selectAllUsers();

	 void deleteUser(int id) throws SQLException;

	 void updateUser(User user) throws SQLException;	 
	
	 boolean isAccountExists(String account) throws SQLException;
	 
	 boolean isPhoneNumberExists(String phoneNumber) throws SQLException;
	 
	 User getUserByAccount(String account) throws SQLException;
	 
	 List<User> selectUsersByType(String type);
	 
	 void insertUcl(Ucl ucl) throws SQLException;
	 
	 boolean isUserRegisteredForClass(User user, Classes classes) throws SQLException;
	 
	 
}
