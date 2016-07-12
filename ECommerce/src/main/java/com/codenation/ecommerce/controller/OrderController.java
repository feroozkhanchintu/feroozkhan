package com.codenation.ecommerce.controller;

import com.codenation.ecommerce.Helpers.AddProductHelper;
import com.codenation.ecommerce.Helpers.SubmitOrderHelper;
import com.codenation.ecommerce.Pojos.AddProduct;
import com.codenation.ecommerce.Pojos.StatusEnum;
import com.codenation.ecommerce.Pojos.SubmitOrder;
import com.codenation.ecommerce.Repository.*;
import com.codenation.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ferooz on 08/07/16.
 */
@RequestMapping(value = "/api")
@RestController
public class OrderController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    AddProductHelper addProductHelper;
    @Autowired
    SubmitOrderHelper submitOrderHelper;

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
        returnBody.put("id", orders.getOrderId());


        return ResponseEntity.ok(returnBody);

    }

    @RequestMapping(value = "/order/{id}/orderLineItem", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody AddProduct addProduct, @PathVariable("id") int orderId)
    {
        return addProductHelper.addProduct(addProduct, orderId);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> submitOrder(@PathVariable("id") int id, @RequestBody SubmitOrder submitOrder) {

        return submitOrderHelper.submitOrder(id, submitOrder);

    }
}


