package com.coffeeshop.store.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StoreNotFoundException extends Exception {

    public StoreNotFoundException(String storeName){
        super(String.format("Store with this NAME: %s, not found in the system.",
                storeName));
    }

    public StoreNotFoundException(Long storeId){
        super(String.format("Store with this ID: %s, not found in the system.",
                storeId));
    }
}
