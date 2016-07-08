package com.codenation.ecommerce.models;

import javax.persistence.*;

/**
 * Created by Ferooz on 08/07/16.
 */

@Entity
@Table(name = "Inventory")
public class Inventory {

    public Inventory(){

    }

    public Inventory(String productId)
    {
        this.ProductId = productId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }


    @Id
    @Column(name="Product_ID")
    private String ProductId;
    @Column(name="Quantity")
    private int Quantity;


}
