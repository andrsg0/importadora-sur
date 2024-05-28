package com.abcg.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordenes")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private Date dateCreation;
    private Date dateReceived;
    private double total;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> detail;
    public Order() {
    }

    public Order(Integer id, String number, Date dateCreation, Date dateReceived, double total) {
        this.id = id;
        this.number = number;
        this.dateCreation = dateCreation;
        this.dateReceived = dateReceived;
        this.total = total;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<OrderDetail> detail) {
        this.detail = detail;
    }
}
