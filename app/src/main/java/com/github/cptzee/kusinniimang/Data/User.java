package com.github.cptzee.kusinniimang.Data;

public class User {
    private int ID;
    private String username;
    private int positionID;
    private int adminID; //<-- accepted by this admin

    public User() {
    }

    public User(String username, int positionID, int adminID) {
        this.username = username;
        this.positionID = positionID;
        this.adminID = adminID;
    }

    public User(int ID, String username, int positionID, int adminID) {
        this.ID = ID;
        this.username = username;
        this.positionID = positionID;
        this.adminID = adminID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}
