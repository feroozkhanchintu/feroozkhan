package com.codenation.ecommerce;


import com.codenation.ecommerce.models.Feedback;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ferooz on 14/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ECommerceApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class FeedbackControllerTest {

    private final String baseURL = "http://localhost:8080";
    private final String feedbackURL = baseURL + "/api/contactus";


    @Test
    public void addFeedback() {

        Response response = given().
                contentType("application/json").
                body(new Feedback("Description")).
                when().
                post(feedbackURL).
                then().
                statusCode(HttpStatus.CREATED.value()).
                body("description", Matchers.is("Description"))
                .extract().response();

        assertNotNull(response.path("id"));

    }

}
