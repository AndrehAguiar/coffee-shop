package com.coffeeshop.store.controller;

import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.util.docs.StoreControllerDocs;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.util.exception.*;
import com.coffeeshop.store.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/store")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StoreController implements StoreControllerDocs {

    private final StoreService storeService;

    @Override
    @GetMapping("/stock/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ProductDTO>> findStock(@PathVariable Long storeId) throws StoreNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.findStock(storeId));
    }

    @Override
    @PutMapping("/stock/{storeId}/add-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> addStockProduct(@PathVariable Long storeId, @Valid @RequestBody ProductDTO productDTO) throws StoreNotFoundException, ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.addStockProduct(storeId, productDTO));
    }

    @Override
    @PutMapping("/stock/{storeId}/remove-product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTO> removeStockProduct(@PathVariable Long storeId, @Valid @RequestBody ProductDTO productDTO) throws StoreNotFoundException, ProductNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.removeStockProduct(storeId, productDTO));
    }

    @Override
    @GetMapping("/stock/{storeId}/to-expire")
    public ResponseEntity<Page<ProductDTO>> findShortExpirationDate(@PathVariable Long storeId) throws StoreNotFoundException, ProductNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.findProductsToExpire(storeId));
    }
}
