package com.example.hospital.table;

import java.io.Serializable;

public class Doctor implements Serializable {
    private static final long serialVersionUID=1L;
    private String docID;
    private String docName;
    private String sex;
    private String depID;
    private String hiredate;

    public Doctor() {
    }

    public Doctor(String docID, String docName, String sex, String depID, String hiredate) {
        this.docID = docID;
        this.docName = docName;
        this.sex = sex;
        this.depID = depID;
        this.hiredate = hiredate;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepID() {
        return depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "docID='" + docID + '\'' +
                ", docName='" + docName + '\'' +
                ", sex='" + sex + '\'' +
                ", depID='" + depID + '\'' +
                ", hiredate='" + hiredate + '\'' +
                '}';
    }
}
