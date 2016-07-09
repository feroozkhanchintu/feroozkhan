package com.codenation.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by Ferooz on 08/07/16.
 */
@Entity
@Table(name = "Orders")
public class Orders {

    public Orders(){

    }

    public Orders(User user)
    {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Order_ID")
    private int orderId;
    @Column(name = "OrderDate")
    private Timestamp orderDate;
    @Column(name = "ShipDate")
    private Timestamp shipDate;
    @Column(name = "Status")
    private String status;
    @Column(name = "ShipID")
    private String shipId;

    @Column(name = "Amount")
    private float amount;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    User user;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "orders", targetEntity = OrderDetails.class, cascade = CascadeType.ALL)
//    private Set<OrderDetails> orderDetails;
//
//    public Set<OrderDetails> getOrderDetails() {
//        return orderDetails;
//    }
//
//    public void setOrderDetails(Set<OrderDetails> orderDetails) {
//        this.orderDetails = orderDetails;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getShipDate() {
        return shipDate;
    }

    public void setShipDate(Timestamp shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }


}
