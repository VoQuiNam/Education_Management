package com.joctopus.dao;

import java.sql.SQLException;
import java.util.List;

import com.joctopus.model.Banner;
import com.joctopus.model.Classes;



public interface BannerDao {
	List<Banner> selectAllBanners();
	
	void insertBanners(Banner banners) throws SQLException;
	
	void updateBanner(Banner banner) throws SQLException;
	
	public Banner selectBanners(int id);
	
	void deleteBanner(int id) throws SQLException;
}
