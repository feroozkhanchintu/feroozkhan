package com.codenation.ecommerce.controller;

/**
 * Created by Ferooz on 07/07/16.
 */

import com.codenation.ecommerce.Repository.InventoryRepository;
import com.codenation.ecommerce.Repository.ProductRepository;
import com.codenation.ecommerce.models.Inventory;
import com.codenation.ecommerce.models.Product;
import com.codenation.ecommerce.models.ModelProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api")
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable("id") int Id) {

        Product product = productRepository.findByIdAndIsAvailable(Id,true);

        if (product != null) {
            ModelProduct returnProduct = new ModelProduct(product.getId(), product.getProductId(), product.getDescription());
            return ResponseEntity.ok(returnProduct);
        }
        HashMap error = new HashMap();
        error.put("detail", "Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<?> getProducts()
    {
        List<Product> target = new ArrayList<>();
        List<ModelProduct> returnProduct = new ArrayList<>();

        for(Product product : productRepository.findByIsAvailable(true)) {
            target.add(product);
            returnProduct.add(new ModelProduct(product.getId(),product.getProductId(),product.getDescription()));
        }
        return ResponseEntity.ok(returnProduct);
    }

    @RequestMapping(value =  "/products", method = RequestMethod.POST)
    public ResponseEntity<?> insertProduct(@RequestBody ModelProduct modelProduct)
    {
        if(modelProduct.getCode() != null) {
            Product prod = productRepository.findByProductId(modelProduct.getCode());

            if(prod.isAvailable() == false) {
                prod.setAvailable(true);
                prod = productRepository.save(prod);
            }
            else if(prod.isAvailable() == true)
            {

            }
            else {
                prod = productRepository.save(new Product(modelProduct.getCode(), modelProduct.getDescription()));
                inventoryRepository.save(new Inventory(prod.getId()));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(prod);
        }
        Map hashMap = new HashMap<>();
        hashMap.put("ERROR", "BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashMap);
    }
    
    @RequestMapping(value =  "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@RequestBody ModelProduct modelProduct, @PathVariable("id") int Id)
    {
        if(modelProduct.getCode() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter Product Code");

        Product product = productRepository.findOne(Id);
        if (product != null) {
            productRepository.save(new Product(Id, modelProduct.getCode(), modelProduct.getDescription()));
            return ResponseEntity.ok(product);
        }
        HashMap error = new HashMap();
        error.put("detail", "Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @RequestMapping(value =  "/products/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> patchProduct(@RequestBody ModelProduct modelProduct, @PathVariable("id") int Id) {
        Product product = productRepository.findOne(Id);
        if (product != null){
            if (modelProduct.getCode() != null)
                product.setProductId(modelProduct.getCode());

            if (modelProduct.getDescription() != null)
                product.setDescription(modelProduct.getDescription());

           Product prod = productRepository.save(product);

            return ResponseEntity.ok(prod);
        }
        HashMap error = new HashMap();
        error.put("detail", "Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int Id) {
        Product product = productRepository.findOne(Id);
        if (product != null) {
            product.setAvailable(false);
            productRepository.save(product);
            HashMap returnValue = new HashMap();
            returnValue.put("detail", "Deleted");
            return ResponseEntity.ok(returnValue);
        }
        HashMap error = new HashMap();
        error.put("detail", "Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }
}
