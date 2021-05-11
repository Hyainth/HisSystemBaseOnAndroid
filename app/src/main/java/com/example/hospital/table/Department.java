package com.example.hospital.table;

import java.io.Serializable;

public class Department implements Serializable {
    private static final long serialVersionUID=1L;
    String depID;
    String depName;
    String phone;

    public Department() {
    }

    public Department(String depID, String depName, String phone) {
        this.depID = depID;
        this.depName = depName;
        this.phone = phone;
    }

    public String getDepID() {
        return this.depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

    public String getDepName() {
        return this.depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return this.depID + "|" + this.depName;
    }
}