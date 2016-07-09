package com.codenation.ecommerce.controller;

import com.codenation.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ferooz on 09/07/16.
 */

@RequestMapping(value = "/api")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "user/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersForUser(@PathVariable("id") int id)
    {
        return ResponseEntity.ok(userRepository.findOne(id));
    }
}
