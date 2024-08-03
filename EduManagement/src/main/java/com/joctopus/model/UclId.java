package com.joctopus.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

public class UclId implements Serializable{

	@Column(name = "id_user")
    private int idUser;

    @Column(name = "class_id")
    private int classId;

    // Default constructor
    public UclId() {}

    // Parameterized constructor
    public UclId(int idUser, int classId) {
        this.idUser = idUser;
        this.classId = classId;
    }

    // Getters and setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    // hashCode and equals methods
    @Override
    public int hashCode() {
        return idUser + classId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UclId uclId = (UclId) obj;
        return idUser == uclId.idUser && classId == uclId.classId;
    }
}
