package com.github.cptzee.kusinniimang.Data;

public class PurchasingOrder {
    private int ID;
    private String item;
    private int productID;
    private int quantity;

    public PurchasingOrder() {
    }

    public PurchasingOrder(int ID, String item, int productID, int quantity) {
        this.ID = ID;
        this.item = item;
        this.productID = productID;
        this.quantity = quantity;
    }

    public PurchasingOrder(String item, int productID, int quantity) {
        this.item = item;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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
