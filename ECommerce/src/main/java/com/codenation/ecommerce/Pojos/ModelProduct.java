package com.codenation.ecommerce.Pojos;

/**
 * Created by Ferooz on 07/07/16.
 */
public class ModelProduct {

    private int id;
    private String code;
    private int qty;


    public ModelProduct(int id, String productCode, String description) {
        this.id = id;
        this.description = description;
        this.code = productCode;
    }


    public ModelProduct(){}

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
