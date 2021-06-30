package com.coffeeshop.headoffice.service;

import com.coffeeshop.headoffice.controller.response.StoreResponse;
import com.coffeeshop.headoffice.model.dto.StoreDTO;
import com.coffeeshop.headoffice.model.entity.Store;
import com.coffeeshop.headoffice.repository.StoreRepository;
import com.coffeeshop.headoffice.resource.StoreResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreResource storeResource;

    public StoreService(StoreRepository storeRepository, StoreResource storeResource) {
        this.storeRepository = storeRepository;
        this.storeResource = storeResource;
    }

    @Transactional
    public StoreDTO create(StoreDTO storeDTO) {
        return new StoreDTO(storeRepository.save(new Store(storeDTO)));
    }

    @Transactional
    public StoreResponse edit(StoreDTO storeDTO) {
        return storeResource.createLinkDetail(new StoreDTO(storeRepository.save(new Store(storeDTO))));
    }

    @Transactional
    public StoreDTO delete(Long id) {
        StoreDTO storeDTO = new StoreDTO(storeRepository.findById(id).orElseThrow());
        storeRepository.delete(new Store(storeDTO));
        return storeDTO;
    }

    @Transactional(readOnly = true)
    public StoreResponse findById(Long id) {
        return storeResource.createLinkDetail(new StoreDTO(storeRepository.findById(id).orElseThrow()));
    }

    @Transactional(readOnly = true)
    public Page<StoreResponse> findAll() {
        List<StoreDTO> storeDTOList = StreamSupport.stream(storeRepository.findAll().spliterator(), false)
                .map(StoreDTO::new).collect(Collectors.toList());
        return new PageImpl<>(storeDTOList.stream()
                .map(storeResource::createLinkDetail).collect(Collectors.toList()));
    }

    @Transactional
    public StoreResponse activate(Long id) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.setInactive(false);
        return storeResource.createLinkDetail(new StoreDTO(storeRepository.save(store)));
    }

    @Transactional
    public StoreResponse inactivate(Long id) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.setInactive(true);
        return storeResource.createLinkDetail(new StoreDTO(storeRepository.save(store)));
    }
}
