package com.codenation.ecommerce.controller;

import com.codenation.ecommerce.Repository.FeedbackRepository;
import com.codenation.ecommerce.models.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * Created by Ferooz on 11/07/16.
 */
@RequestMapping(value = "/api")
@RestController
public class FeedbackController {

    @Autowired
    FeedbackRepository feedbackRepository;

    @RequestMapping(value = "/contactus", method = RequestMethod.POST)
    public ResponseEntity<?> getFeedback(@RequestBody Feedback feedback)
    {
        feedback.setTimestamp(new Timestamp(System.currentTimeMillis()));
        feedback = feedbackRepository.save(feedback);

        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }
}
