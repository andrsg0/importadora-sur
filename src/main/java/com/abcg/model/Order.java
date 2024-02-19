package com.abcg.model;

import java.util.Date;

public class Order {
    private Integer id;
    private String number;
    private Date dateCreation;
    private Date dateReceived;

    public Order() {
    }

    public Order(Integer id, String number, Date dateCreation, Date dateReceived) {
        this.id = id;
        this.number = number;
        this.dateCreation = dateCreation;
        this.dateReceived = dateReceived;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }
}
