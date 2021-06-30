package com.coffeeshop.store.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductInactiveCantBeIncrementedException extends Exception {

    public ProductInactiveCantBeIncrementedException(Long productId){
        super(String.format("Product with this ID: %s, is inactive and can't be incremented.",
                productId));
    }
}
