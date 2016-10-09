package com.codenation.ecommerce.Helpers;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.codenation.ecommerce.Pojos.LogInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Ferooz on 11/07/16.
 */
@Component
public class AWSMessagingService {
    AmazonSQS amazonSQS;

    private final String queueURL = "cnu2016_fkhan_assignment05";

    public AWSMessagingService() {

        amazonSQS = new AmazonSQSClient(new DefaultAWSCredentialsProviderChain());
        Region usWest2 = Region.getRegion(Regions.US_EAST_1);
        amazonSQS.setRegion(usWest2);
    }

    public void sendMessageToQueue(LogInformation log)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String logJson = null;
        try {
            logJson = objectMapper.writeValueAsString(log);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //TODO
        }
        String SQSQueueUrl = amazonSQS.getQueueUrl(queueURL).getQueueUrl();

        amazonSQS.sendMessage(SQSQueueUrl,logJson);
        System.out.println(SQSQueueUrl);
    }
}
