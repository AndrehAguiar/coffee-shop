package com.coffeeshop.headoffice.controller;

import com.coffeeshop.headoffice.controller.response.HeadOfficeResponse;
import com.coffeeshop.headoffice.model.dto.HeadOfficeDTO;
import com.coffeeshop.headoffice.service.HeadOfficeService;
import com.coffeeshop.headoffice.controller.docs.HeadOfficeControllerDoc;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/head-office")
public class HeadOfficeController implements HeadOfficeControllerDoc {

    private final HeadOfficeService headOfficeService;

    public HeadOfficeController(HeadOfficeService headOfficeService) {
        this.headOfficeService = headOfficeService;
    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<HeadOfficeResponse> create(@Valid @RequestBody HeadOfficeDTO headOfficeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(headOfficeService.create(headOfficeDTO));
    }

    @Override
    @PutMapping("/edit")
    public ResponseEntity<HeadOfficeResponse> edit(@Valid @RequestBody HeadOfficeDTO headOfficeDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(headOfficeService.update(headOfficeDTO));
    }

    @Override
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<HeadOfficeDTO> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(headOfficeService.delete(id));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HeadOfficeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(headOfficeService.findById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<HeadOfficeResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(headOfficeService.findAll());
    }

    @Override
    @PutMapping("/activate/{id}")
    public ResponseEntity<HeadOfficeResponse> activate(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(headOfficeService.activate(id));
    }

    @Override
    @PutMapping("/inactivate/{id}")
    public ResponseEntity<HeadOfficeResponse> inactivate(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(headOfficeService.inactivate(id));
    }

}
