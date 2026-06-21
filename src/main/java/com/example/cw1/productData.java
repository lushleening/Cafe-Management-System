package com.example.cw1;

import java.util.Date;

public class productData {

    private Integer ID;
    private String productID;
    private String productname;
    private String type;
    private Integer stock;
    private Double price;
    private String image;
    private Date date;
    private Integer quantity;

    public productData(Integer ID, String productID, String productname, String type, Integer stock, Double price,Date date, String image) {
        this.ID = ID;
        this.productID = productID;
        this.productname = productname;
        this.type = type;
        this.stock = stock;
        this.quantity = stock;  // Set quantity = stock for consistency
        this.price = price;
        this.image = image;
        this.date = date;
    }
    // Fix your second constructor to match the parameter order in your SQL query
    public productData(Integer ID, String productID, String productname, String type, Integer quantity, Double price, String image, Date date) {
        this.ID = ID;
        this.productID = productID;
        this.productname = productname;
        this.type = type;
        this.quantity = quantity;
        this.stock = quantity;
        this.price = price;
        this.image = image;
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductname() {
        return productname;
    }

    public String getType() {
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String setImage(String image) {
        this.image = image;
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Integer getQuantity() {
        return quantity;
    }


}
