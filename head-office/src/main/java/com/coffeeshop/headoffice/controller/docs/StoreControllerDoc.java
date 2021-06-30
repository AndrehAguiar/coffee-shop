package com.coffeeshop.headoffice.controller.docs;

import com.coffeeshop.headoffice.controller.response.StoreResponse;
import com.coffeeshop.headoffice.model.dto.StoreDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Api("Manages Stores Head Office")
public interface StoreControllerDoc {

    @ApiOperation(value = "Head Office store creation operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success creating the Head Office store."),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    ResponseEntity<StoreDTO> create(StoreDTO storeDTO);

    @ApiOperation(value = "Head Office updating operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success updating the Head Office."),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<StoreResponse> edit(StoreDTO storeDTO);

    @ApiOperation(value = "Head Office removing operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success removing the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<StoreDTO> delete(Long id);

    @ApiOperation(value = "Head Office finding operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success removing the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<StoreResponse> findById(Long id);

    @ApiOperation(value = "Head Office listing all operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success removing the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<Page<StoreResponse>> findAll();

    @ApiOperation(value = "Head Office activating operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success activating the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<StoreResponse> activate(Long id);

    @ApiOperation(value = "Head Office inactivating operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success inactivating the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<StoreResponse> inactivate(Long id);

}
