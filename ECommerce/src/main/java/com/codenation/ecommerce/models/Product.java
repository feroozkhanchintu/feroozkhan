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
    @Column(name="Product_ID")
    private String code;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="category_id")
    private Integer categoryId;
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

    public Product(String code, String name, String description, Integer categoryId, float unitPrice, float buyPrice, boolean isAvailable) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.unitPrice = unitPrice;
        this.buyPrice = buyPrice;
        this.isAvailable = isAvailable;
    }

    public Product(String code, String description) {
        this.code = code;
        this.description = description;
        name = null;
        categoryId = null;
        unitPrice = 0;
        buyPrice = 0;
        this.isAvailable = true;
    }

    public Product(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
        name = null;
        categoryId = null;
        unitPrice = 0;
        buyPrice = 0;
        this.isAvailable = true;

    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
