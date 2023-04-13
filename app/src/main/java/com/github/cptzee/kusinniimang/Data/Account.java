package com.github.cptzee.kusinniimang.Data;

import com.github.cptzee.kusinniimang.Data.Enums.AccountType;

public class Account {
    private int ID;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String contactNo;
    private int credentialID;
    private AccountType type;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getCredentialID() {
        return credentialID;
    }

    public void setCredentialID(int credentialID) {
        this.credentialID = credentialID;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
