package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import com.joctopus.model.Ucl;
import com.joctopus.model.User;

public interface ClassesUserDao {
	void insertClassUser(Ucl ucl) throws SQLException;

	Ucl selectUcl(int Id);

	List<Ucl> selectAllUcl();

	void deleteUcl(int id_user,int class_id) throws SQLException;
}
