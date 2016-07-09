package com.codenation.ecommerce.models;

import com.codenation.ecommerce.PrimaryKey.OrderDetailsPrimaryKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ferooz on 08/07/16.
 */
@Entity
@Table(name = "OrderDetails")
public class OrderDetails implements Serializable{

    @EmbeddedId
    private OrderDetailsPrimaryKey orderDetailsPrimaryKey;

    @Column(name = "Price")
    private float price;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Total")
    private float total;


    public OrderDetails() {
    }

    public OrderDetailsPrimaryKey getOrderDetailsPrimaryKey() {
        return orderDetailsPrimaryKey;
    }

    public void setOrderDetailsPrimaryKey(OrderDetailsPrimaryKey orderDetailsPrimaryKey) {
        this.orderDetailsPrimaryKey = orderDetailsPrimaryKey;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }


}
