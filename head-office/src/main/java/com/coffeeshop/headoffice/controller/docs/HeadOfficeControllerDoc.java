package com.coffeeshop.headoffice.controller.docs;

import com.coffeeshop.headoffice.controller.response.HeadOfficeResponse;
import com.coffeeshop.headoffice.model.dto.HeadOfficeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Api("Manages Head Office")
public interface HeadOfficeControllerDoc {

    @ApiOperation(value = "Head Office creation operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success creating the Head Office."),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    ResponseEntity<HeadOfficeResponse> create(HeadOfficeDTO headOfficeDTO);

    @ApiOperation(value = "Head Office updating operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success updating the Head Office."),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<HeadOfficeResponse> edit(HeadOfficeDTO headOfficeDTO);

    @ApiOperation(value = "Head Office removing operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success removing the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<HeadOfficeDTO> delete(Long id);

    @ApiOperation(value = "Head Office finding operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success removing the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<HeadOfficeResponse> findById(Long id);

    @ApiOperation(value = "Head Office listing all operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success removing the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<Page<HeadOfficeResponse>> findAll();

    @ApiOperation(value = "Head Office activating operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success activating the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<HeadOfficeResponse> activate(Long id);

    @ApiOperation(value = "Head Office inactivating operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success inactivating the Head Office."),
            @ApiResponse(code = 404, message = "Head Office not found.")
    })
    ResponseEntity<HeadOfficeResponse> inactivate(Long id);

}
