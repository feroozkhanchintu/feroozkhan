package com.codenation.ecommerce.controller;

import com.codenation.ecommerce.Helpers.AddProductHelper;
import com.codenation.ecommerce.Helpers.SubmitOrderHelper;
import com.codenation.ecommerce.Pojos.AddProduct;
import com.codenation.ecommerce.Pojos.SubmitOrder;
import com.codenation.ecommerce.Repository.*;
import com.codenation.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<?> gethelp() {
        Map help = new HashMap();
        help.put("Health", "health");
        return ResponseEntity.ok(help);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersForOrderId(@PathVariable("id") int id) {
        Orders order=ordersRepository.findByIdAndDeleted(id,false);
        if(order!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
        Map returnBody = new HashMap();
        returnBody.put("ERROR", "NOTFOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(returnBody);
    }


    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder() {
//        if(user != null) {
//            String emailId = user.getEmail();
//            user = userRepository.findByEmail(emailId);
//
//            if (user == null)
//                user = userRepository.save(new User(emailId));
//
//            Orders orders = ordersRepository.save(new Orders(user));
//
//            Map returnBody = new HashMap();
//            returnBody.put("UserId", user.getUserId());
//            returnBody.put("id", orders.getId());
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(returnBody);
//        }
        User user;
        User usr = new User();
        user = userRepository.save(usr);
        Orders orders = ordersRepository.save(new Orders(user));

        Map returnBody = new HashMap();
        returnBody.put("UserId", user.getUserId());
        returnBody.put("id", orders.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(returnBody);

    }

    @RequestMapping(value = "/orders/{id}/orderLineItem", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody AddProduct addProduct, @PathVariable("id") int orderId)
    {
        return addProductHelper.addProduct(addProduct, orderId);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> submitOrder(@PathVariable("id") int id, @RequestBody SubmitOrder submitOrder) {

        return submitOrderHelper.submitOrder(id, submitOrder);

    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable("id") int id) {

        Orders orders = ordersRepository.findByIdAndDeleted(id, false);
        if(orders == null) {
            Map returnBody = new HashMap();
            returnBody.put("ERROR", "NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(returnBody);
        }
        if(!orders.isDeleted()) {
            orders.setDeleted(true);
            ordersRepository.save(orders);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orders);
        }
        Map error = new HashMap();
        error.put("ERROR","ORDER NOT PRESENT");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }
}


