package com.joctopus.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Banner")
public class Banner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BannerID")
	protected int BannerID;

	@Column(name = "Title")
	protected String Title;

	@Column(name = "Description")
	protected String Description;

	@Column(name = "ImageUrl")
	protected String ImageUrl;

	@Column(name = "StartDate")
	protected LocalDate StartDate;

	@Column(name = "EndDate")
	protected LocalDate EndDate;

	@Column(name = "IsActive")
	protected boolean IsActive;

	@Column(name = "Position")
	protected String Position;

	@Column(name = "OrderIndex")
	protected int OrderIndex;

	public Banner() {
	}

	public Banner(int bannerID, String title, String description, String imageUrl, LocalDate startDate,
			LocalDate endDate, boolean isActive, String position, int orderIndex) {
		super();
		this.BannerID = bannerID;
		this.Title = title;
		this.Description = description;
		this.ImageUrl = imageUrl;
		this.StartDate = startDate;
		this.EndDate = endDate;
		this.IsActive = isActive;
		this.Position = position;
		this.OrderIndex = orderIndex;
	}

	public Banner(String title, String description, String imageUrl, LocalDate startDate, LocalDate endDate,
			boolean isActive, String position, int orderIndex) {
		super();
		this.Title = title;
		this.Description = description;
		this.ImageUrl = imageUrl;
		this.StartDate = startDate;
		this.EndDate = endDate;
		this.IsActive = isActive;
		this.Position = position;
		this.OrderIndex = orderIndex;
	}

	public int getBannerID() {
		return BannerID;
	}

	public void setBannerID(int bannerID) {
		this.BannerID = bannerID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.ImageUrl = imageUrl;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.StartDate = startDate;
	}

	public LocalDate getEndDate() {
		return EndDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.EndDate = endDate;
	}

	public boolean isIsActive() {
		return IsActive;
	}

	public void setIsActive(boolean isActive) {
		this.IsActive = isActive;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		this.Position = position;
	}

	public int getOrderIndex() {
		return OrderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.OrderIndex = orderIndex;
	}

}
