package com.example.cw1;

import java.util.Date;

public class customersData {
    private Integer id;
    private Integer customerId;
    private Double total;
    private Date date;
    private String em_username;

    public customersData(Integer id, Integer customerId, Double total, Date date, String em_username) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
        this.date = date;
        this.em_username = em_username;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }
    public Double getTotal() {
        return total;
    }
    public Date getDate() {
        return date;
    }
    public String getEmUsername() {
        return em_username;
    }

}

