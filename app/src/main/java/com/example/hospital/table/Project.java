package com.example.hospital.table;

import java.io.Serializable;

public class Project implements Serializable {
    private static final long serialVersionUID=1L;
    private String projID;
    private String projName;
    private String depID;
    private String unit;
    private String price;
    private String notes;

    public Project() {
    }

    public Project(String projID, String projName, String depID, String unit, String price, String notes) {
        this.projID = projID;
        this.projName = projName;
        this.depID = depID;
        this.unit = unit;
        this.price = price;
        this.notes = notes;
    }

    public String getProjID() {
        return this.projID;
    }

    public void setProjID(String projID) {
        this.projID = projID;
    }

    public String getProjName() {
        return this.projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getDepID() {
        return this.depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

//    public String toString() {
//        return this.projID + "|" + this.projName;
//    }

    @Override
    public String toString() {
        return projID+"|"+projName;
    }
}