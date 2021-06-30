package com.coffeeshop.store.serviceTest;

import com.coffeeshop.store.builder.ProductDTOBuilder;
import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.entity.Product;
import com.coffeeshop.store.util.exception.*;
import com.coffeeshop.store.util.mapper.ProductMapper;
import com.coffeeshop.store.repository.ProductRepository;
import com.coffeeshop.store.util.resource.ProductResource;
import com.coffeeshop.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static final long INVALID_PRODUCT_ID = 0L;
    private static final String INVALID_PRODUCT_EAN = "000000000000000";
    private static final String VALID_PRODUCT_EAN = "12345678910";
    private static final String INVALID_PRODUCT_NAME = "Cafe";

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductResource productResource;

    @InjectMocks
    private ProductService productService;


    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    void whenProductCantBeCreated() throws ProductAlreadyRegisteredException {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedSaveProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findByEan(expectedProductDTO.getEan()))
                .thenReturn(Optional.empty());
        when(productRepository.save(expectedSaveProduct))
                .thenReturn(expectedSaveProduct);

        // Then
        ProductDTO createdProductDTO = productService.createProduct(expectedProductDTO);

        assertThat(createdProductDTO.getProductId(),
                is(equalTo(expectedProductDTO.getProductId())));
        assertThat(createdProductDTO.getProductName(),
                is(equalTo(expectedProductDTO.getProductName())));
        assertThat(createdProductDTO.getDescription(),
                is(equalTo(expectedProductDTO.getDescription())));
        assertThat(createdProductDTO.getEan(),
                is(equalTo(expectedProductDTO.getEan())));
        assertThat(createdProductDTO.getInStock(),
                is(greaterThanOrEqualTo(0D)));

    }

    @Test
    void whenProductIsAlreadyRegisteredThrowException() {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product duplicatedProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findByEan(expectedProductDTO.getEan()))
                .thenReturn(Optional.of(duplicatedProduct));

        // Then
        assertThrows(ProductAlreadyRegisteredException.class,
                () -> productService.createProduct(expectedProductDTO));
    }

    @Test
    void whenFindProductByEan() throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedFoundProduct = productMapper.toEntity(expectedProductDTO);
        ProductResponse productResponse = productResource.createLinkDetail(expectedProductDTO);

        // When
        doReturn(Optional.of(expectedFoundProduct))
                .when(productRepository).findByEan(expectedFoundProduct.getEan());

        // Then
        ProductResponse foundResponse = productService.findByEan(VALID_PRODUCT_EAN);
        assertThat(foundResponse, is(productResponse));
    }

    @Test
    void whenFindProductByNameThrowException() {
        // Given

        // When
        doReturn(Optional.empty())
                .when(productRepository).findByProductName(INVALID_PRODUCT_NAME);

        // Then
        assertThrows(NoSuchElementException.class,
                () -> productService.findByName(INVALID_PRODUCT_NAME));
    }

    @Test
    void whenFindAllProductsReturnProductList() {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findAll())
                .thenReturn(Collections.singletonList(expectedProduct));
        when(productRepository.save(expectedProduct))
                .thenReturn(expectedProduct);

        // Then
        Page<ProductResponse> foundProductDTOList = productService.findAll();
        assertThat(foundProductDTOList.getSize(), is(1));
    }

    @Test
    void whenFindProductByName() {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedFoundProduct = productMapper.toEntity(expectedProductDTO);

        // When
/*        when(productRepository.findByProductName(expectedProductDTO.getProductName()))
                .thenReturn(Collections.singletonList(expectedFoundProduct));

        // Then
        List<ProductDTO> foundProductDTOList = productService.findByName(expectedProductDTO.getProductName());
        assertThat(foundProductDTOList, is(not(empty())));
        assertThat(foundProductDTOList.get(0).getProductName(), is(equalTo("Batata")));*/
    }

    @Test
    void whenProductEanNotFoundThrowException() {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(productRepository.findByEan(INVALID_PRODUCT_EAN))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(ProductNotFoundException.class,
                () -> productService.findByEan(INVALID_PRODUCT_EAN));
    }

    @Test
    void whenProductIdNotFoundThrowException() {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(productRepository.findById(INVALID_PRODUCT_ID))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(ProductNotFoundException.class,
                () -> productService.findById(INVALID_PRODUCT_ID));
    }

    @Test
    void whenFindAllActiveProductsIsCalled() {
        // Given
        ProductDTO expectedFoundProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedFoundProduct = productMapper.toEntity(expectedFoundProductDTO);

        // When
        //when(productRepository.findAllActive())
        //        .thenReturn(Collections.singletonList(expectedFoundProduct));

        // Then
        //List<ProductDTO> foundProductDTOList = productService.findAllActive();

        //assertThat(foundProductDTOList, is(not(empty())));
        //assertThat(foundProductDTOList.get(0).getProductIsInactive(), is(equalTo(false)));
    }

    @Test
    void whenFindAllActiveProductsReturnEmptyList() {
        // Given
        ProductDTO expectedFoundProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toEntity(expectedFoundProductDTO);
        expectedProduct.setProductIsInactive(true);

        // When
        doReturn(Optional.empty())
                .when(productRepository).findAllActive();

        // Then
        assertThrows(NoSuchElementException.class,
                () -> productService.findAllActive());
    }

    @Test
    void whenProductCantBeInactivatedThrowException() {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedSavedProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findByEan(expectedProductDTO.getEan()))
                .thenReturn(Optional.of(expectedSavedProduct));

        // Then
        assertThrows(ProductInStockIsGreaterThenZeroException.class,
                () -> productService.inactivateProduct(expectedProductDTO, false));
    }

    @Test
    void whenProductIsDeactivated() throws ProductInStockIsGreaterThenZeroException, ProductNotFoundException {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        expectedProductDTO.setInStock(0D);
        Product expectedProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findByEan(expectedProductDTO.getEan()))
                .thenReturn(Optional.of(expectedProduct));

        // Then
        productService.inactivateProduct(expectedProductDTO, true);
        assertThat(expectedProductDTO.getProductIsInactive(), equalTo(true));
    }

    @Test
    void whenDecrementResultIsLowerThanZeroThrowException() {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findById(expectedProductDTO.getProductId()))
                .thenReturn(Optional.of(expectedProduct));

        // Then
        Double quantityToDecrement = 30D;
        assertThrows(ProductInStockIsLowerThanZero.class,
                () -> productService.decrement(expectedProductDTO.getProductId(), quantityToDecrement));

    }

    @Test
    void whenDecrementProductInStock() throws ProductInStockIsLowerThanZero, ProductNotFoundException {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findById(expectedProductDTO.getProductId()))
                .thenReturn(Optional.of(expectedProduct));

        // Then
        Double quantityToDecrement = 10D;
        Double expectedQuantityInStock = expectedProductDTO.getInStock() - quantityToDecrement;
        ProductDTO productDTOAfterDecrement = productService.decrement(expectedProductDTO.getProductId(), quantityToDecrement);
        assertThat(expectedQuantityInStock, greaterThan(0D));
        assertThat(expectedQuantityInStock, equalTo(productDTOAfterDecrement.getInStock()));
    }

    @Test
    void whenIncrementProductInStock() throws ProductNotFoundException, ProductInactiveCantBeIncrementedException {
        // Given
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Product expectedProduct = productMapper.toEntity(expectedProductDTO);

        // When
        when(productRepository.findById(expectedProductDTO.getProductId()))
                .thenReturn(Optional.of(expectedProduct));

        // Then
        Double quantityToIncrement = 10D;
        Double expectedQuantityAfterIncrement = expectedProductDTO.getInStock() + quantityToIncrement;

        ProductDTO incrementedProductDTO = productService.increment(
                expectedProductDTO.getProductId(), quantityToIncrement);
        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedProductDTO.getInStock()));
    }

    @Test
    void whenLookForInvalidEanThrowException() {
        // When Given
        when(productRepository.findByEan(INVALID_PRODUCT_EAN))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(ProductNotFoundException.class,
                () -> productService.checkIfExists(INVALID_PRODUCT_EAN));
    }

    @Test
    void whenLookForInvalidIdThrowException() {
        // When Given
        when(productRepository.findById(INVALID_PRODUCT_ID))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(ProductNotFoundException.class,
                () -> productService.checkIfExists(INVALID_PRODUCT_ID));
    }

}
