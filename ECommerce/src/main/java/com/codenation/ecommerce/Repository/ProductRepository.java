package com.codenation.ecommerce.Repository;

import com.codenation.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * Created by Ferooz on 07/07/16.
 */

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product findByIdAndIsAvailable(Integer id, boolean isavailable);
    List<Product> findByIsAvailable(boolean isavailable);
    Product findByCode(String productCode);
}
