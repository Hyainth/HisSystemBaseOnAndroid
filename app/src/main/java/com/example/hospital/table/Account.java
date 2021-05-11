package com.example.hospital.table;

public class Account {
    private String userID;
    private String pwd;
    private int kind;

    public Account() {
    }

    public Account(String userID, String pwd) {
        this.userID = userID;
        this.pwd = pwd;
    }

    public Account(String userID, String pwd, int kind) {
        this.userID = userID;
        this.pwd = pwd;
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userID='" + userID + '\'' +
                ", pwd='" + pwd + '\'' +
                ", kind=" + kind +
                '}';
    }
}
