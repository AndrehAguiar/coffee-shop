package com.coffeeshop.store.repository;

import com.coffeeshop.store.model.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT product FROM Product product WHERE product.productIsInactive = 0")
    Optional<List<Product>> findAllActive();

    Optional<List<Product>> findByProductName(String name);

    Optional<Product> findByEan(String productEan);

}
