package com.codenation.ecommerce.Helpers;

import com.codenation.ecommerce.Repository.*;
import com.codenation.ecommerce.models.Inventory;
import com.codenation.ecommerce.models.OrderDetails;
import com.codenation.ecommerce.models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ferooz on 11/07/16.
 */
@RestController
public class SubmitOrderHelper {
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

    public ResponseEntity<?> submitOrder(int id) {
        System.out.println(ordersRepository);
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
        orders.setOrderDetails(orderDetails);

        orders = ordersRepository.save(orders);
        return ResponseEntity.ok(orders);
    }

}
