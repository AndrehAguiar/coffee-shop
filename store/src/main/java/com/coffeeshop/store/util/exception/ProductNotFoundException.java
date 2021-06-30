package com.coffeeshop.store.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(String productEan){
        super(String.format("Product with this EAN: %s, not found in the system.",
                productEan));
    }

    public ProductNotFoundException(Long productId){
        super(String.format("Product with this ID: %s, not found in the system.",
                productId));
    }
}
