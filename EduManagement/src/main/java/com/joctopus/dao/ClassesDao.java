package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import com.joctopus.model.Classes;
import com.joctopus.model.Ucl;

public interface ClassesDao {
	void insertClasses(Classes classes) throws SQLException;

	Classes selectClasses(int Id);

	List<Classes> selectAllClasses();

	void deleteClasses(int id) throws SQLException;

	void updateClasses(Classes classes) throws SQLException;

	boolean isClassNameExists(String account) throws SQLException;
	
	Classes getClassById(int id);
	
	 public List<Classes> selectClassesByStatus(String status);
}
