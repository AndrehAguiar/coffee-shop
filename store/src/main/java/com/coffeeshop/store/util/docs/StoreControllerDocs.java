package com.coffeeshop.store.util.docs;

import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.util.exception.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface StoreControllerDocs {

    @ApiOperation(value = "Listing stock store operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success getting the Store stock."),
            @ApiResponse(code = 400, message = "Invalid Store given."),
            @ApiResponse(code = 404, message = "Store with given id not found.")
    })
    ResponseEntity<Page<ProductDTO>> findStock(Long storeId) throws StoreNotFoundException, ProductNotFoundException;

    @ApiOperation(value = "Add stock product operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success adding the stock product."),
            @ApiResponse(code = 400, message = "Invalid Product given."),
            @ApiResponse(code = 404, message = "Store with given id not found.")
    })
    ResponseEntity<ProductResponse> addStockProduct(Long storeId, ProductDTO productDTO) throws StoreNotFoundException, ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException;

    @ApiOperation(value = "Remove stock product operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success removing the stock product."),
            @ApiResponse(code = 400, message = "Invalid Product given."),
            @ApiResponse(code = 404, message = "Store with given id not found.")
    })
    ResponseEntity<ProductDTO> removeStockProduct(Long storeId, ProductDTO productDTO) throws StoreNotFoundException, ProductNotFoundException;

    @ApiOperation(value = "Listing stock products with short expiration date.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success listing the stock products with short expiration date."),
            @ApiResponse(code = 400, message = "Invalid Product given."),
            @ApiResponse(code = 404, message = "Store with given id not found.")
    })
    ResponseEntity<Page<ProductDTO>> findShortExpirationDate(Long storeId) throws StoreNotFoundException, ProductNotFoundException;

}
