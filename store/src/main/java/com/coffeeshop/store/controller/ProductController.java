package com.coffeeshop.store.controller;

import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.util.docs.ProductControllerDocs;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.util.exception.*;
import com.coffeeshop.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController implements ProductControllerDocs {

    private final ProductService productService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) throws ProductAlreadyRegisteredException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(productDTO));
    }

    @Override
    @PutMapping("/inactivate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTO> inactiveProduct(@Valid @RequestBody ProductDTO productDTO) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.inactivateProduct(productDTO, true));
    }

    @Override
    @PutMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTO> activeProduct(@Valid @RequestBody ProductDTO productDTO) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.inactivateProduct(productDTO, false));
    }

    @Override
    @PutMapping("/{productId}/increment/{incrementValue}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTO> incrementProduct(@PathVariable Long productId, @RequestBody Double incrementValue) throws ProductNotFoundException, ProductInactiveCantBeIncrementedException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.increment(productId, incrementValue));
    }

    @Override
    @PutMapping("/{productId}/decrement/{decrementValue}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTO> decrementProduct(@PathVariable Long productId, Double decrementValue) throws ProductNotFoundException, ProductInStockIsLowerThanZero {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.decrement(productId, decrementValue));
    }

    @Override
    @GetMapping("/by-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ProductResponse>> findByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findByName(name));
    }

    @Override
    @GetMapping("/by-ean/{ean}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> findByEan(@PathVariable String ean) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findByEan(ean));
    }

    @Override
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ProductResponse>> listProducts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findAll());
    }

    @Override
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ProductResponse>> listActiveProducts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findAllActive());
    }

}
