package com.codenation.ecommerce.PrimaryKey;

import com.codenation.ecommerce.models.Orders;
import com.codenation.ecommerce.models.Product;
import com.codenation.ecommerce.models.User;

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

    @ManyToOne
    @JoinColumn(name = "Order_ID")
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    Product product;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

