package com.codenation.ecommerce;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.codenation.ecommerce.InterceptHandlers.LogInformationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Ferooz on 07/07/16.
 */
@SpringBootApplication
public class ECommerceApplication extends WebMvcConfigurerAdapter {

    @Autowired
    LogInformationInterceptor logInformationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInformationInterceptor);
    }

//    @Bean
//    public AmazonSQS amazonSQS()
//    {
//        AmazonSQS amazonSQS = new AmazonSQSClient(new DefaultAWSCredentialsProviderChain());
//        Region usWest2 = Region.getRegion(Regions.US_EAST_1);
//        amazonSQS.setRegion(usWest2);
//        return amazonSQS;
//    }

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}
