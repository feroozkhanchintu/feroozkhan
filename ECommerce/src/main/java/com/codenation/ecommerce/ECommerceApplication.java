package com.codenation.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Ferooz on 07/07/16.
 */
@SpringBootApplication
public class ECommerceApplication extends WebMvcConfigurerAdapter {

//    @Autowired
//    LogInformationInterceptor logInformationInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logInformationInterceptor);
//    }


    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}
