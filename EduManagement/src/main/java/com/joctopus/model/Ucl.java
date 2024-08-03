package com.joctopus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "users_classes")
public class Ucl implements Serializable {

	@EmbeddedId
	private UclId id;

	@ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user")
	protected User id_user;

	@ManyToOne
    @MapsId("classId")
    @JoinColumn(name = "class_id")
	protected Classes class_id;

	// Constructor mặc định
	public Ucl() {
	}

	public Ucl(User id_user, Classes class_id) {
		this.id = new UclId(id_user.getId(), class_id.getId());
		this.id_user = id_user;
		this.class_id = class_id;
	}

	public User getId_user() {
		return id_user;
	}

	public void setId_user(User id_user) {
		this.id_user = id_user;
	}

	public Classes getClass_id() {
		return class_id;
	}

	public void setClass_id(Classes class_id) {
		this.class_id = class_id;
	}

}
