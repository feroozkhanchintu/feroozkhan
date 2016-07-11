package com.codenation.ecommerce.Helpers;

import com.codenation.ecommerce.PrimaryKey.OrderDetailsPrimaryKey;
import com.codenation.ecommerce.Repository.*;
import com.codenation.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ferooz on 10/07/16.
 */
public class AddProductHelper {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InventoryRepository inventoryRepository;


    public AddProductHelper() {

    }

    public ResponseEntity<?> addProduct(AddProduct addProduct, int orderId) {
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
}
