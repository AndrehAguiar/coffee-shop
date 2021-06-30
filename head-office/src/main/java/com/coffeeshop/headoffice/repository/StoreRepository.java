package com.coffeeshop.headoffice.repository;

import com.coffeeshop.headoffice.model.entity.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface StoreRepository extends CrudRepository<Store, Long>{
}
