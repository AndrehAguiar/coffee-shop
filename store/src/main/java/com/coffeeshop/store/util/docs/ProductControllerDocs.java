package com.coffeeshop.store.util.docs;

import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.util.exception.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductControllerDocs {

    @ApiOperation(value = "Product creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success Product creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO) throws ProductAlreadyRegisteredException;

    @ApiOperation(value = "Product inactivation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Product inactivation"),
            @ApiResponse(code = 400, message = "Product in stock is greater than zero."),
            @ApiResponse(code = 404, message = "Product not found.")
    })
    ResponseEntity<ProductDTO> inactiveProduct(ProductDTO productDTO) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException;

    @ApiOperation(value = "Product activation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Product activation"),
            @ApiResponse(code = 404, message = "Product not found.")
    })
    ResponseEntity<ProductDTO> activeProduct(ProductDTO productDTO) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException;


    @ApiOperation(value = "Product increment by a given id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Product creation"),
            @ApiResponse(code = 404, message = "Product with given id not found.")
    })
    ResponseEntity<ProductDTO> incrementProduct(Long productId, Double incrementValue) throws ProductNotFoundException, ProductInactiveCantBeIncrementedException;

    @ApiOperation(value = "Product decrement by a given id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Product creation"),
            @ApiResponse(code = 400, message = "Product in stock is lower than request to decrement."),
            @ApiResponse(code = 404, message = "Product with given id not found.")
    })
    ResponseEntity<ProductDTO> decrementProduct(Long productId, Double decrementValue) throws ProductNotFoundException, ProductInStockIsLowerThanZero;

    @ApiOperation(value = "Returns product found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success product found in the system"),
            @ApiResponse(code = 404, message = "Product with given name not found.")
    })
    ResponseEntity<Page<ProductResponse>> findByName(@PathVariable String name) throws ProductNotFoundException;

    @ApiOperation(value = "Returns product found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success product found in the system"),
            @ApiResponse(code = 404, message = "Product with given ean not found.")
    })
    ResponseEntity<ProductResponse> findByEan(@PathVariable String ean) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException;

    @ApiOperation(value = "Returns a list of all products registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all products registered in the system"),
    })
    ResponseEntity<Page<ProductResponse>> listProducts();

    @ApiOperation(value = "Returns a list of all active products registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all active products registered in the system"),
    })
    ResponseEntity<Page<ProductResponse>> listActiveProducts();
}
