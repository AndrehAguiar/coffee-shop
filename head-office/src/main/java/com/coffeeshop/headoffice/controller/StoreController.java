package com.coffeeshop.headoffice.controller;

import com.coffeeshop.headoffice.controller.response.StoreResponse;
import com.coffeeshop.headoffice.model.dto.StoreDTO;
import com.coffeeshop.headoffice.service.StoreService;
import com.coffeeshop.headoffice.controller.docs.StoreControllerDoc;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController implements StoreControllerDoc {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<StoreDTO> create(@Valid @RequestBody StoreDTO storeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeService.create(storeDTO));
    }

    @Override
    @PutMapping("/edit")
    public ResponseEntity<StoreResponse> edit(@Valid @RequestBody StoreDTO storeDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.edit(storeDTO));
    }

    @Override
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<StoreDTO> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(storeService.delete(id));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.findById(id));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<StoreResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.findAll());
    }

    @Override
    @PutMapping("/{id}/activate")
    public ResponseEntity<StoreResponse> activate(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.activate(id));
    }

    @Override
    @PutMapping("/{id}/inactivate")
    public ResponseEntity<StoreResponse> inactivate(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.inactivate(id));
    }
}
