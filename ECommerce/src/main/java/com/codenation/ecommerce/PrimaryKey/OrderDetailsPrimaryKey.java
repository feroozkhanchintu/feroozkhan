package com.codenation.ecommerce.PrimaryKey;

import com.codenation.ecommerce.models.Orders;
import com.codenation.ecommerce.models.Product;
import com.codenation.ecommerce.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Ferooz on 09/07/16.
 */
@Embeddable
public class OrderDetailsPrimaryKey implements Serializable {

    @Column(name = "Order_ID")
    private int orderId;
    @Column(name = "Product_ID")
    private int productId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}

