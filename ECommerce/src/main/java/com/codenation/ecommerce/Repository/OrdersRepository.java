package com.codenation.ecommerce.Repository;

import com.codenation.ecommerce.models.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Ferooz on 08/07/16.
 */
public interface OrdersRepository extends CrudRepository<Orders, Integer> {

    Orders findById(int orderId);


}
