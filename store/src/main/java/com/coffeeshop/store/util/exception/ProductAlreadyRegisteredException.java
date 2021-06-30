package com.coffeeshop.store.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductAlreadyRegisteredException extends Exception {

    public ProductAlreadyRegisteredException(String productEan){
        super(String.format("Product with this EAN: %s, already register in the system",
                productEan)
        );
    }

    public ProductAlreadyRegisteredException(Long productId){
        super(String.format("Product with this EAN: %s, already register in the system",
                productId)
        );
    }
}