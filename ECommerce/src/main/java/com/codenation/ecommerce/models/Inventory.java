package com.codenation.ecommerce.models;

import javax.persistence.*;

/**
 * Created by Ferooz on 08/07/16.
 */

@Entity
@Table(name = "Inventory")
public class Inventory {

    @Id
    @Column(name="Product_ID")
    private int ProductId;
    @Column(name="Quantity")
    private int Quantity;

    public Inventory(){

    }

    public Inventory(int productId, int qty)
    {
        this.ProductId = productId;
        this.Quantity = qty;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }





}
