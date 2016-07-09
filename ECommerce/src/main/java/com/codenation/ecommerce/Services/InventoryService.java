package com.codenation.ecommerce.Services;

import com.codenation.ecommerce.Repository.InventoryRepository;
import com.codenation.ecommerce.models.GetProduct;
import com.codenation.ecommerce.models.Inventory;
import com.codenation.ecommerce.models.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ferooz on 09/07/16.
 */
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    public boolean checkForInventory(GetProduct product)
    {
        System.out.println(product.getProductId());
        Inventory inventory = inventoryRepository.findOne(product.getProductId());
        if(inventory != null)
            if(inventory.getQuantity() >  product.getQuantity())
                return true;

        return false;
    }
}
