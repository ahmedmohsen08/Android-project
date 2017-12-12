package com.example.ahmedmohsen.project;

/**
 * Created by ahmedmohsen on 12/3/2017.
 */

public class Products {
    int ProID;
    String ProName;
    int Price;
    int Quantity;
    int CatID;

    public int getProID() {
        return ProID;
    }

    public void setProID(int proID) {
        ProID = proID;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getCatID() {
        return CatID;
    }

    public void setCatID(int catID) {
        CatID = catID;
    }
}
