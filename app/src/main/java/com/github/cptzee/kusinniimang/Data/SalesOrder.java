package com.github.cptzee.kusinniimang.Data;

public class SalesOrder {
    private int ID;
    private int userID;
    private int productID;
    private int quantity;

    public SalesOrder() {
    }

    public SalesOrder(int ID, int userID, int productID, int quantity) {
        this.ID = ID;
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public SalesOrder(int userID, int productID, int quantity) {
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
