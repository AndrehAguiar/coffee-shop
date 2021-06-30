package com.coffeeshop.store.service;

import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.dto.StoreDTO;
import com.coffeeshop.store.model.entity.Store;
import com.coffeeshop.store.util.exception.*;
import com.coffeeshop.store.util.mapper.StoreMapper;
import com.coffeeshop.store.repository.StoreRepository;
import com.coffeeshop.store.util.resource.ProductResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final ProductResource productResource;
    private final StoreMapper storeMapper = StoreMapper.INSTANCE;

    public StoreService(StoreRepository repository, ProductResource productResource) {
        this.storeRepository = repository;
        this.productResource = productResource;
    }

    @Transactional(readOnly = true)
    public StoreDTO checkIfStoreExists(Long storeId) throws StoreNotFoundException {
        Optional<Store> optStore = storeRepository.findById(storeId);
        if (optStore.isEmpty())
            throw new StoreNotFoundException(storeId);
        return storeMapper.toDTO(optStore.get());
    }

    @Transactional
    public ProductResponse getProductInStock(ProductDTO productDTO, Set<ProductDTO> stock) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException {
        for (ProductDTO dto : stock) {
            if (dto.getEan().equals(productDTO.getEan())) {
                return productResource.createLinkDetail(dto);
            }
        }
        throw new ProductNotFoundException(productDTO.getEan());
    }

    @Transactional
    public List<ProductDTO> getProductsInStockToExpire(Set<ProductDTO> stock) throws ProductNotFoundException {
        List<ProductDTO> productsToExpire = new ArrayList<>();
        for (ProductDTO dto : stock) {
            if (dto.getExpirationDate().isBefore(LocalDate.now().plus(15, ChronoUnit.DAYS))) {
                productsToExpire.add(dto);
            }
        }
        return productsToExpire;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findStock(Long storeId) throws StoreNotFoundException {
        StoreDTO storeDTO = checkIfStoreExists(storeId);
        return new PageImpl<>(new ArrayList<>(storeDTO.getProducts()));
    }

    @Transactional
    public ProductResponse addStockProduct(Long storeId, ProductDTO productDTO) throws StoreNotFoundException, ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException {
        StoreDTO storeDTO = checkIfStoreExists(storeId);
        storeDTO.getProducts().add(productDTO);
        storeRepository.save(storeMapper.toEntity(storeDTO));
        return getProductInStock(productDTO, storeDTO.getProducts());
    }

    @Transactional
    public ProductDTO removeStockProduct(Long storeId, ProductDTO productDTO) throws StoreNotFoundException, ProductNotFoundException {
        StoreDTO storeDTO = checkIfStoreExists(storeId);
        boolean productRemoved = storeDTO.getProducts().removeIf(it -> it.getEan().equals(productDTO.getEan()));
        storeRepository.save(storeMapper.toEntity(storeDTO));
        if (productRemoved)
            return productDTO;
        throw new ProductNotFoundException(productDTO.getEan());
    }


    public Page<ProductDTO> findProductsToExpire(Long storeId) throws StoreNotFoundException, ProductNotFoundException {
        StoreDTO storeDTO = checkIfStoreExists(storeId);
        return new PageImpl<>(getProductsInStockToExpire(storeDTO.getProducts()));
    }
}
