package com.codenation.ecommerce;

/**
 * Created by Ferooz on 14/07/16.
 */

import com.codenation.ecommerce.Pojos.AddProduct;
import com.codenation.ecommerce.Pojos.SubmitOrder;
import com.codenation.ecommerce.Repository.InventoryRepository;
import com.codenation.ecommerce.Repository.OrderDetailsRepository;
import com.codenation.ecommerce.Repository.OrdersRepository;
import com.codenation.ecommerce.Repository.ProductRepository;
import com.codenation.ecommerce.models.Inventory;
import com.codenation.ecommerce.models.Orders;
import com.codenation.ecommerce.models.Product;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ECommerceApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class OrderControllerTest {
    private final String baseURL = "http://localhost:8080/api/";
    private final String ordersURL = baseURL + "orders";
    private final String ordersURLWithID = baseURL + "orders/{id}";
    private final String ordersLineURL = ordersURL + "/{id}/orderLineItem";
    private final String healthURL = baseURL + "health";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    private Product product1;
    private int product1Id;
    private AddProduct addProduct;
    private SubmitOrder submitOrder;

    private Orders orders;
    private int orderId;

    @Before
    public void setUp() {
        product1 = new Product("prod127345", "product", "product1 description", null, (float)100.00, (float)70.00, true);
        product1 = productRepository.save(product1);

        orders = new Orders();
        orders = ordersRepository.save(orders);

        orderId = orders.getId();
        product1Id = product1.getId();
        inventoryRepository.save(new Inventory(product1Id, 20));

        addProduct = new AddProduct();
        addProduct.setProduct_id(product1Id);
        addProduct.setQty(10);

        submitOrder = new SubmitOrder();
        submitOrder.setUser_name("fer");
        submitOrder.setStatus("Confirm");
        submitOrder.setAddress("Address");

    }

    @Test
    public void checkHealth() {

        given().
            contentType("application/json").
            when().
            get(healthURL).
            then().
            statusCode(HttpStatus.OK.value());

    }

    @Test
    public void createOrder()
    {
        given().
                contentType("application/json").
                when().
                post(ordersURL).
                then().
                statusCode(HttpStatus.CREATED.value());

    }

    @Test
    public void getOrders()
    {
        int mockId = 233223;
        given().
                contentType("application/json").
                when().
                get(ordersURLWithID, orderId).
                then().
                statusCode(HttpStatus.OK.value());

        given().
                contentType("application/json").
                when().
                get(ordersURLWithID, mockId).
                then().
                statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void createorderLineItem()
    {
        given().
                contentType("application/json")
                .body(addProduct)
                .when()
                .post(ordersLineURL, orderId)
                .then()
                .statusCode(HttpStatus.CREATED.value());

        addProduct.setProduct_id(1231231231);
        given().
                contentType("application/json")
                .body(addProduct)
                .when()
                .post(ordersLineURL, orderId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void submitorder() {
        given().
                contentType("application/json")
                .body(addProduct)
                .when()
                .post(ordersLineURL, orderId)
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given().
                contentType("application/json")
                .body(submitOrder)
                .when()
                .patch(ordersURLWithID, orderId)
                .then()
                .statusCode(HttpStatus.OK.value());


        submitOrder.setAddress(null);
        given().
                contentType("application/json")
                .body(submitOrder)
                .when()
                .patch(ordersURLWithID, orderId)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void deleteorder() {
        int mockId = 12323;
        given().
                contentType("application/json")
                .when()
                .delete(ordersURLWithID, orderId)
                .then()
                .statusCode(HttpStatus.OK.value());

        given().
                contentType("application/json")
                .when()
                .delete(ordersURLWithID, mockId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());


    }


    @After
    public void tearDown()
    {
        inventoryRepository.deleteAll();
        orderDetailsRepository.deleteAll();
        productRepository.deleteAll();
        ordersRepository.deleteAll();
    }
}
