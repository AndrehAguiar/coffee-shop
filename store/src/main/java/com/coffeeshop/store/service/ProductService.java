package com.coffeeshop.store.service;

import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.entity.Product;
import com.coffeeshop.store.util.exception.*;
import com.coffeeshop.store.util.mapper.ProductMapper;
import com.coffeeshop.store.repository.ProductRepository;
import com.coffeeshop.store.util.resource.ProductResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductResource productResource;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public ProductService(ProductRepository repository, ProductResource productResource) {
        this.productRepository = repository;
        this.productResource = productResource;
    }

    public List<ProductResponse> addLinksToItemList(@NotNull List<ProductDTO> productDTOList) {
        return productDTOList.stream()
                .map(x -> {
                    try {
                        return findByEan(x.getEan());
                    } catch (ProductNotFoundException | ProductInStockIsGreaterThenZeroException | ProductInStockIsLowerThanZero | ProductInactiveCantBeIncrementedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    // Check is product is already registered
    @Transactional(readOnly = true)
    public void checkIfAlreadyRegistered(String productEan) throws ProductAlreadyRegisteredException {
        Optional<Product> optSavedProduct = productRepository.findByEan(productEan);
        if (optSavedProduct.isPresent()) throw new ProductAlreadyRegisteredException(productEan);
    }

    // Check is product is already registered
    @Transactional(readOnly = true)
    public void checkIfAlreadyRegistered(Long productId) throws ProductAlreadyRegisteredException {
        Optional<Product> optSavedProduct = productRepository.findById(productId);
        if (optSavedProduct.isPresent()) throw new ProductAlreadyRegisteredException(productId);
    }

    // Check is product is already registered
    @Transactional(readOnly = true)
    public void checkIfExists(String productEan) throws ProductNotFoundException {
        Optional<Product> optSavedProduct = productRepository.findByEan(productEan);
        if (optSavedProduct.isEmpty()) throw new ProductNotFoundException(productEan);
    }

    // Check is product is already registered
    @Transactional(readOnly = true)
    public void checkIfExists(Long productId) throws ProductNotFoundException {
        Optional<Product> optSavedProduct = productRepository.findById(productId);
        if (optSavedProduct.isEmpty()) throw new ProductNotFoundException(productId);
    }

    // Create new product store
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) throws ProductAlreadyRegisteredException {
        checkIfAlreadyRegistered(productDTO.getEan());
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    // Find Product by id
    @Transactional(readOnly = true)
    public ProductDTO findById(Long productId) throws ProductNotFoundException {
        checkIfExists(productId);
        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isEmpty()) throw new ProductNotFoundException(productId);
        return productMapper.toDTO(optProduct.orElseThrow());
    }

    // Find Product by ean
    @Transactional(readOnly = true)
    public ProductResponse findByEan(String productEan) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException {
        checkIfExists(productEan);
        return productResource.createLinkDetail(productMapper
                .toDTO(productRepository.findByEan(productEan).orElseThrow()));
    }

    // Find All Active
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllActive() {
        List<ProductDTO> productDTOList = productRepository.findAllActive().orElseThrow()
                .stream().map(productMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(addLinksToItemList(productDTOList));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findByName(String productName) {
        List<ProductDTO> productDTOList = productRepository.findByProductName(productName).orElseThrow()
                .stream().map(productMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(addLinksToItemList(productDTOList));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll() {
        Iterable<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = StreamSupport.stream(productList.spliterator(), false)
                .map(productMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(addLinksToItemList(productDTOList));
    }

    // Invactive a product
    @Transactional
    public ProductDTO inactivateProduct(ProductDTO productDTO, Boolean isInactive) throws ProductInStockIsGreaterThenZeroException, ProductNotFoundException {
        checkIfExists(productDTO.getEan());
        if (productDTO.getInStock() > 0)
            throw new ProductInStockIsGreaterThenZeroException(productDTO.getEan(), productDTO.getInStock());
        productDTO.setProductIsInactive(isInactive);
        Product product = productMapper.toEntity(productDTO);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    // Decrement products in stock
    @Transactional
    public ProductDTO decrement(Long productId, Double quantityToDecrement) throws ProductInStockIsLowerThanZero, ProductNotFoundException {
        checkIfExists(productId);
        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            if (optProduct.get().getInStock() - quantityToDecrement < 0)
                throw new ProductInStockIsLowerThanZero(productId, quantityToDecrement);
            Double inStock = product.getInStock();
            product.setInStock(inStock - quantityToDecrement);
            productRepository.save(product);
            return productMapper.toDTO(product);
        }
        throw new ProductNotFoundException(productId);
    }

    @Transactional
    public ProductDTO increment(Long productId, Double quantityToIncrement) throws ProductNotFoundException, ProductInactiveCantBeIncrementedException {
        checkIfExists(productId);
        Product product = productRepository.findById(productId).orElseThrow();
        if (!product.getProductIsInactive()) {
            product.setInStock(product.getInStock() + quantityToIncrement);
            productRepository.save(product);
            return productMapper.toDTO(product);
        }
        throw new ProductInactiveCantBeIncrementedException(productId);
    }
}
