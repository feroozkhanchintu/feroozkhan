package com.codenation.ecommerce.Repository;

import com.codenation.ecommerce.PrimaryKey.OrderDetailsPrimaryKey;
import com.codenation.ecommerce.models.OrderDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Ferooz on 08/07/16.
 */
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, OrderDetailsPrimaryKey> {

}
