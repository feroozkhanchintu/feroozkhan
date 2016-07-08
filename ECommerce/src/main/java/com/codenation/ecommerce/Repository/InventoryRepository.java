package com.codenation.ecommerce.Repository;

import com.codenation.ecommerce.models.Inventory;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Ferooz on 08/07/16.
 */
public interface InventoryRepository extends CrudRepository<Inventory, String> {

}
