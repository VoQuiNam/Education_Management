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
@Table(name = "classes")
public class Classes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;
	
	
	@Column(name = "class_name")
	protected String class_name;
	
	
	@Column(name = "class")
	protected String eduClass;
	
	@Column(name = "study_time")
	protected LocalDate study_time;
	
	@Column(name = "subject")
	protected String subject;
	

	@Column(name = "address")
	protected String address;
	
	@Column(name = "session")
	protected String session;
	
	@Column(name = "status")
	protected String status;
	
	@Column(name = "numberOfStudents")
	protected int numberOfStudents;
	
	@Column(name = "requeststatus")
	protected String requeststatus;
	
	@ManyToOne
	@JoinColumn(name = "users")
	protected User users;
	
	
	

	public Classes() {
	}

	
	
	public Classes(int id, String class_name, String eduClass, LocalDate study_time, String subject, String address,
			String session, String status, int numberOfStudents, String requeststatus, User users) {
		super();
		this.id = id;
		this.class_name = class_name;
		this.eduClass = eduClass;
		this.study_time = study_time;
		this.subject = subject;
		this.address = address;
		this.session = session;
		this.status = status;
		this.numberOfStudents = numberOfStudents;
		this.requeststatus = requeststatus;
		this.users = users;
	}



	public Classes(String class_name, String eduClass, LocalDate study_time, String subject, String address,
			String session, String status, String requeststatus) {
		super();

		this.class_name = class_name;
		this.eduClass = eduClass;
		this.study_time = study_time;
		this.subject = subject;
		this.address = address;
		this.session = session;
		this.status = status;
		this.requeststatus = requeststatus;
	}


	






//	public Classes(int id, String class_name, String eduClass, LocalDate study_time, String subject, String address,
//			String session, String status, String requeststatus, User users) {
//		super();
//		this.id = id;
//		this.class_name = class_name;
//		this.eduClass = eduClass;
//		this.study_time = study_time;
//		this.subject = subject;
//		this.address = address;
//		this.session = session;
//		this.status = status;
//		this.requeststatus = requeststatus;
//		this.users = users;
//	}




	public Classes(String class_name, String eduClass, LocalDate study_time, String subject, String address,
			String session, String status, String requeststatus, User users) {
		super();
		this.class_name = class_name;
		this.eduClass = eduClass;
		this.study_time = study_time;
		this.subject = subject;
		this.address = address;
		this.session = session;
		this.status = status;
		this.requeststatus = requeststatus;
		this.users = users;
	}


	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getClass_name() {
		return class_name;
	}




	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}




	public String getEduClass() {
		return eduClass;
	}




	public void setEduClass(String eduClass) {
		this.eduClass = eduClass;
	}




	public LocalDate getStudy_time() {
		return study_time;
	}




	public void setStudy_time(LocalDate study_time) {
		this.study_time = study_time;
	}




	public String getSubject() {
		return subject;
	}




	public void setSubject(String subject) {
		this.subject = subject;
	}




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public String getSession() {
		return session;
	}




	public void setSession(String session) {
		this.session = session;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequeststatus() {
		return requeststatus;
	}




	public void setRequeststatus(String requeststatus) {
		this.requeststatus = requeststatus;
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}




	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}


	public User getUsers() {
		return users;
	}




	public void setUsers(User users) {
		this.users = users;
	}



	

}
