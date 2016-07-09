package com.codenation.ecommerce.controller;

import com.codenation.ecommerce.Repository.OrderDetailsRepository;
import com.codenation.ecommerce.Repository.OrdersRepository;
import com.codenation.ecommerce.Repository.ProductRepository;
import com.codenation.ecommerce.Repository.UserRepository;
import com.codenation.ecommerce.Services.InventoryService;
import com.codenation.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ferooz on 08/07/16.
 */
@RequestMapping(value = "/api")
@RestController
public class OrderController {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersForOrderId(@PathVariable("id") int id) {
        return ResponseEntity.ok(ordersRepository.findOne(id));
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody User user) {

        String emailId = user.getEmail();
        user = userRepository.findByEmail(emailId);

        if(user == null)
            user = userRepository.save(new User(emailId));

        Orders orders = ordersRepository.save(new Orders(user));

        Map returnBody = new HashMap();
        returnBody.put("UserId", user.getUserId());
        returnBody.put("OrderId", orders.getOrderId());

        return ResponseEntity.ok(returnBody);

    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody GetProduct getProduct, @PathVariable("id") int id)
    {
        InventoryService inventoryService = new InventoryService();
        System.out.println(getProduct.getProductId() + "  " + getProduct.getQuantity() + " " );

        boolean checkForInventory = inventoryService.checkForInventory(getProduct);

        System.out.println(getProduct.getProductId() + "  " + getProduct.getQuantity() + " " + checkForInventory);
        //if(checkForInventory)
        {
            //Product product = productRepository.findOne(orderDetails.getOrderDetailsPrimaryKey().getProduct().getId());

        }
        return ResponseEntity.ok("");
    }

}
