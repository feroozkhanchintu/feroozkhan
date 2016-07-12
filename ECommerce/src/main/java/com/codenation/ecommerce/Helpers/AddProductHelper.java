package com.codenation.ecommerce.Helpers;

import com.codenation.ecommerce.Pojos.AddProduct;
import com.codenation.ecommerce.Pojos.StatusEnum;
import com.codenation.ecommerce.PrimaryKey.OrderDetailsPrimaryKey;
import com.codenation.ecommerce.Repository.*;
import com.codenation.ecommerce.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ferooz on 10/07/16.
 */
@Component
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
        Inventory inventory = inventoryRepository.findOne(addProduct.getProduct_id());
        boolean isProdAvail = false;

        System.out.println(inventory);
        OrderDetails orderDetails = new OrderDetails();

        if (inventory != null)
            if (inventory.getQuantity() > (addProduct.getQty() + orderDetails.getQuantity()))
                isProdAvail = true;

        if (isProdAvail) {
            Product product = productRepository.findOne(addProduct.getProduct_id());
            Orders orders = ordersRepository.findOne(orderId);
            orders.setStatus(StatusEnum.ONCART.toString());
            orders = ordersRepository.save(orders);

            int quantity = addProduct.getQty();
            OrderDetailsPrimaryKey orderDetailsPrimaryKey = new OrderDetailsPrimaryKey();
            orderDetailsPrimaryKey.setOrderId(orders.getId());
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

            return ResponseEntity.status(HttpStatus.CREATED).body(orderDetails);

        }
        Map error = new HashMap();
        error.put("ERROR", "Available quantity is less");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }
}
