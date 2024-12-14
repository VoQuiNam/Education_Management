package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import com.joctopus.model.Banner;
import com.joctopus.model.Classes;
import com.joctopus.model.Comments;



public interface CommentsDao {
	List<Comments> selectAllComments();
}
