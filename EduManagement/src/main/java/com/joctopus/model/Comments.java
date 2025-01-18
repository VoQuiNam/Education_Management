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
@Table(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	protected User user_id;

	@Column(name = "content")
	protected String content;

	@Column(name = "created_at")
	protected LocalDate created_at;

	@ManyToOne
	@JoinColumn(name = "class_id")
	protected Classes class_id;

	@ManyToOne
	@JoinColumn(name = "parent_comment_id")
	private Comments parent_comment_id; // Add a field for the parent comment (null for top-level comments)

	public Comments() {

	}

	public Comments(int id, User user_id, String content, LocalDate created_at, Classes class_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.content = content;
		this.created_at = created_at;
		this.class_id = class_id;

	}

	public Comments(User user_id, String content, LocalDate created_at, Classes class_id) {
		super();
		this.user_id = user_id;
		this.content = content;
		this.created_at = created_at;
		this.class_id = class_id;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}

	public Classes getClass_id() {
		return class_id;
	}

	public void setClass_id(Classes class_id) {
		this.class_id = class_id;
	}

	public Comments getParentComment() {
		return parent_comment_id;
	}

	public void setParentComment(Comments parentComment) {
		this.parent_comment_id = parentComment;
	}

}
