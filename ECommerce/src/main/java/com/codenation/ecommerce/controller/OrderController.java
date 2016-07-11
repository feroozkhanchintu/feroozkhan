package com.codenation.ecommerce.controller;

import com.codenation.ecommerce.Helpers.AddProductHelper;
import com.codenation.ecommerce.Helpers.SubmitOrderHelper;
import com.codenation.ecommerce.PrimaryKey.OrderDetailsPrimaryKey;
import com.codenation.ecommerce.Repository.*;
import com.codenation.ecommerce.models.*;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
    public ResponseEntity<?> addProduct(@RequestBody AddProduct addProduct, @PathVariable("id") int orderId)
    {

//        AddProductHelper addProductHelper = new AddProductHelper();
//        return addProductHelper.addProduct(addProduct, id);

        Inventory inventory = inventoryRepository.findOne(addProduct.getProductId());
        boolean isProdAvail = false;

        OrderDetails orderDetails = new OrderDetails();

        if (inventory != null)
            if (inventory.getQuantity() > (addProduct.getQuantity() + orderDetails.getQuantity()))
                isProdAvail = true;

        if (isProdAvail) {
            Product product = productRepository.findOne(addProduct.getProductId());
            Orders orders = ordersRepository.findOne(orderId);
            orders.setStatus("ON CART");
            orders = ordersRepository.save(orders);

            int quantity = addProduct.getQuantity();
            OrderDetailsPrimaryKey orderDetailsPrimaryKey = new OrderDetailsPrimaryKey();
            orderDetailsPrimaryKey.setOrderId(orders.getOrderId());
            orderDetailsPrimaryKey.setProductId(product.getId());
            orderDetails = orderDetailsRepository.findOne(orderDetailsPrimaryKey);
            if (orderDetails != null)
                quantity += orderDetails.getQuantity();

            orderDetails = new OrderDetails();

            orderDetails.setOrderDetailsPrimaryKey(orderDetailsPrimaryKey);
            orderDetails.setPrice(product.getUnitPrice());
            orderDetails.setQuantity(quantity);
            orderDetails.setOrders(orders);
            orderDetails.setProduct(product);

            orderDetails = orderDetailsRepository.save(orderDetails);

            return ResponseEntity.ok(orderDetails);

        }
        Map error = new HashMap();
        error.put("ERROR", "Available quantity is less");
        return ResponseEntity.ok(error);

    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> submitOrder(@PathVariable("id") int id, @RequestBody SubmitOrder submitOrder) {

//        SubmitOrderHelper submitOrderHelper = new SubmitOrderHelper();
//        return submitOrderHelper.submitOrder(id);

        User user = userRepository.findByEmail(submitOrder.getEmail());
        if(user == null)
            user = userRepository.save(new User(submitOrder.getEmail()));

        user.setCustomerName(submitOrder.getName());
        user.setAddressLine1(submitOrder.getAddress());
        user.setMobileNo(submitOrder.getPhone());
        userRepository.save(user);

        Orders orders = ordersRepository.findByOrderId(id);
        Set<OrderDetails> orderDetails = orders.getOrderDetails();

        float totalAmount = 0;

        Inventory inventory;
        for (OrderDetails orderDet : orderDetails) {

            int orderedQuantity = orderDet.getQuantity();
            inventory = inventoryRepository.findOne(orderDet.getProduct().getId());
            if (orderDet.getQuantity() > inventory.getQuantity()) {
                Map error = new HashMap();
                error.put("ERROR", "Product quantity not available");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        }

        for (OrderDetails orderDet : orderDetails) {
            inventory = inventoryRepository.findOne(orderDet.getProduct().getId());
            inventory.setQuantity(inventory.getQuantity() - orderDet.getQuantity());
            inventoryRepository.save(inventory);
            float amountForOrderLine = orderDet.getPrice() * orderDet.getQuantity();
            orderDet.setTotal(amountForOrderLine);
            totalAmount += amountForOrderLine;
        }

        orders.setOrderDate(new Timestamp(System.currentTimeMillis()));
        orders.setStatus("Submitted");
        orders.setAmount(totalAmount);
        orders.setUser(user);
        orders.setOrderDetails(orderDetails);

        orders = ordersRepository.save(orders);
        return ResponseEntity.ok(orders);

    }
}


