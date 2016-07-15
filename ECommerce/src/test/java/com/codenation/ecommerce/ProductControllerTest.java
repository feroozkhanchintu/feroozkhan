package com.codenation.ecommerce;

import com.codenation.ecommerce.Pojos.ModelProduct;
import com.codenation.ecommerce.Repository.InventoryRepository;
import com.codenation.ecommerce.Repository.ProductRepository;
import com.codenation.ecommerce.models.Inventory;
import com.codenation.ecommerce.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ferooz on 12/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ECommerceApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class ProductControllerTest {


    private final String baseURL = "http://localhost:8080";
    private final String productURL = baseURL + "/api/products";
    private final String productURLWithID = productURL + "/{id}";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    private Product product1;
    private Product product2;
    private Product product3;

    private int product1Id;
    private int product2Id;

    @Before
    public void setUp() {
        product1 = new Product("product1", "product", "product1 description", null, (float)100.00, (float)70.00, true);
        product2 = new Product("product2", "product", "product2 description", null, (float)900.00, (float)570.00, true);
        product3 = new Product("product3", "product", "product3 description", null, (float)900.00, (float)570.00, true);

        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        product1Id = product1.getId();
        product2Id = product2.getId();

        inventoryRepository.save(new Inventory(product1Id, 20));
        inventoryRepository.save(new Inventory(product2Id, 30));

    }

    @Test
    public void addProduct() {

        Response response = given().
                contentType("application/json").
                body(product3).
                when().
                post(productURL).
                then().
                statusCode(HttpStatus.CREATED.value()).
                body("code", Matchers.is(product3.getCode())).
                body("description", Matchers.is(product3.getDescription()))
                .extract().response();

        assertNotNull(response.path("id"));

        product3.setCode(null);
        response = given().
                contentType("application/json").
                body(product3).
                when().
                post(productURL).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().response();


//        response = given().
//                contentType("application/json").
//                body(product2).
//                when().
//                post(productURL).
//                then().
//                statusCode(HttpStatus.BAD_REQUEST.value())
//                .extract().response();
    }

    @Test
    public void getProduct(){

        int someId = 23434;

        Response response = given().
                contentType("application/json").
                when().
                get(productURLWithID, product1Id).
                then().
                statusCode(HttpStatus.OK.value()).
                body("id", Matchers.is(product1.getId())).
                body("code", Matchers.is(product1.getCode())).
                body("description", Matchers.is(product1.getDescription()))
                .extract().response();

        response = given().
                contentType("application/json").
                when().
                get(productURLWithID, someId).
                then().
                statusCode(HttpStatus.NOT_FOUND.value())
                .extract().response();

    }

    @Test
    public void getAllProduct(){


        Response response = given().
                contentType("application/json").
                when().
                get(productURL).
                then().
                statusCode(HttpStatus.OK.value())
                .extract().response();

    }

    @Test
    public void patchProduct()
    {
        String mockString = "abcd";
        int mockId = 12323;
        ModelProduct modelProduct = new ModelProduct();
        modelProduct.setDescription(mockString);
        modelProduct.setId(product1Id);

        Response response = given().
                contentType("application/json").
                body(modelProduct).
                when().
                patch(productURLWithID, product1Id).
                then().
                statusCode(HttpStatus.OK.value()).
                body("description", Matchers.is(mockString))
                .extract().response();

        response = given().
                contentType("application/json").
                body(modelProduct).
                when().
                patch(productURLWithID, mockId).
                then().
                statusCode(HttpStatus.NOT_FOUND.value())
                .extract().response();
    }

    @Test
    public void updateProduct()
    {
        String mockCode = "abcd";
        int mockId = 32434;
        ModelProduct modelProduct = new ModelProduct();
        modelProduct.setId(product1Id);
        modelProduct.setCode(mockCode);

        Response response = given().
                contentType("application/json").
                body(modelProduct).
                when().
                put(productURLWithID, product1Id).
                then().
                statusCode(HttpStatus.OK.value()).
                body("code", Matchers.is(mockCode))
                .extract().response();

        response = given().
                contentType("application/json").
                body(modelProduct).
                when().
                put(productURLWithID, mockId).
                then().
                statusCode(HttpStatus.NOT_FOUND.value())
                .extract().response();

        modelProduct.setCode(null);

        response = given().
                contentType("application/json").
                body(modelProduct).
                when().
                put(productURLWithID, mockId).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().response();

    }

    @Test
    public void deleteProduct()
    {
        int mockInput = 324994;
        Response response = given().
                contentType("application/json").
                when().
                delete(productURLWithID, product1Id).
                then().
                statusCode(HttpStatus.OK.value()).
                extract().response();

        response = given().
                contentType("application/json").
                when().
                delete(productURLWithID, mockInput).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();
    }

    @After
    public void tearDown()
    {
        inventoryRepository.deleteAll();
        productRepository.deleteAll();
    }

}
