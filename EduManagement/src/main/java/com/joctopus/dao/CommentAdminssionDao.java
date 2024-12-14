package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import com.joctopus.model.Classes;
import com.joctopus.model.Comments;
import com.joctopus.model.Ucl;
import com.joctopus.model.User;

public interface CommentAdminssionDao {
	List<Comments> selectAllComments();
	
	public Classes getClassDetails(int classId);
	
	 List<Comments> selectCommentsByClassId(int class_id);
	 
	 public void postComments(Comments comments) throws SQLException;
	 
	
}
