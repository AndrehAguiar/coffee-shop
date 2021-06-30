package com.coffeeshop.store.repository;

import com.coffeeshop.store.model.entity.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface StoreRepository extends CrudRepository<Store, Long> {
}
