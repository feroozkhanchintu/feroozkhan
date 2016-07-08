package com.codenation.ecommerce.models;
import javax.persistence.*;


/**
 * Created by Ferooz on 07/07/16.
 */

@Entity
@Table(name = "Product")
public class Product {

    @Column(name="id")
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Id
    private int id;
    @Column(name="product_ID")
    private String product_ID;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="contentDescription")
    private String contentDescription;
    @Column(name="unitPrice")
    private float unitPrice;
    @Column(name="buyPrice")
    private float buyPrice;
    @Column(name="isAvailable")
    private boolean isAvailable;


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public Product(){

    }

    public Product(int Id, String product_ID, String name, String description, String contentDescription, float unitPrice, float buyPrice, boolean isAvailable) {
        this.product_ID = product_ID;
        this.name = name;
        this.description = description;
        this.contentDescription = contentDescription;
        this.unitPrice = unitPrice;
        this.buyPrice = buyPrice;
        this.isAvailable = isAvailable;
    }

    public Product(String product_ID, String description) {
        this.product_ID = product_ID;
        this.description = description;
        name = null;
        contentDescription = null;
        unitPrice = 0;
        buyPrice = 0;
        this.isAvailable = true;
    }

    public Product(int id, String product_ID, String description) {
        this.id = id;
        this.product_ID = product_ID;
        this.description = description;
        name = null;
        contentDescription = null;
        unitPrice = 0;
        buyPrice = 0;
        this.isAvailable = true;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }


}
