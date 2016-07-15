package com.codenation.ecommerce.Repository;

import com.codenation.ecommerce.models.Orders;
import com.codenation.ecommerce.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Ferooz on 08/07/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByCustomerName(String customerName);
    User findByEmail(String email);


}