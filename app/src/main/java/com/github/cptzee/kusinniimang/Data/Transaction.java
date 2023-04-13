package com.github.cptzee.kusinniimang.Data;

public class Transaction {
    private int ID;
    private int orderID;
    private double totalPrice;
    private long date;

    public Transaction() {
    }

    public Transaction(int ID, int orderID, double totalPrice, long date) {
        this.ID = ID;
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public Transaction(int orderID, double totalPrice, long date) {
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
