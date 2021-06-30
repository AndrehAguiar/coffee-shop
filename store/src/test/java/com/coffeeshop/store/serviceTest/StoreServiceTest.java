package com.coffeeshop.store.serviceTest;

import com.coffeeshop.store.builder.ProductDTOBuilder;
import com.coffeeshop.store.builder.StoreDTOBuilder;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.dto.StoreDTO;
import com.coffeeshop.store.model.entity.Store;
import com.coffeeshop.store.util.exception.ProductNotFoundException;
import com.coffeeshop.store.util.exception.StoreNotFoundException;
import com.coffeeshop.store.util.mapper.StoreMapper;
import com.coffeeshop.store.repository.StoreRepository;
import com.coffeeshop.store.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    private static final Long INVALID_STORE_ID = 0L;
    private static final Long VALID_STORE_ID = 1L;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    private final StoreMapper storeMapper = StoreMapper.INSTANCE;

    @Test
    void whenFindStoreStockByStoreIdReturnProductsPage() throws StoreNotFoundException {
        // Given
        StoreDTO expectedStoreDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        ProductDTO productDTOInStock = ProductDTOBuilder.builder().build().toProductDTO();
        Set<ProductDTO> stock = expectedStoreDTO.getProducts();
        stock.add(productDTOInStock);
        expectedStoreDTO.setProducts(stock);
        Store expectedStore = storeMapper.toEntity(expectedStoreDTO);

        // When
        when(storeRepository.findById(VALID_STORE_ID))
                .thenReturn(Optional.of(expectedStore));

        // Then
        Page<ProductDTO> foundStock = storeService.findStock(expectedStore.getStoreId());
        Page<ProductDTO> expectedStoreDTOStock = new PageImpl<>(new ArrayList<>(expectedStoreDTO.getProducts()));
        assertThat(foundStock, is(expectedStoreDTOStock));
    }

    @Test
    void whenFindStoreStockByStoreIdReturnEmptyProductsPage() throws StoreNotFoundException {
        // Given
        StoreDTO expectedStoreDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        Store expectedStore = storeMapper.toEntity(expectedStoreDTO);

        // When
        when(storeRepository.findById(VALID_STORE_ID))
                .thenReturn(Optional.of(expectedStore));

        // Then
        Page<ProductDTO> foundStock = storeService.findStock(expectedStore.getStoreId());
        Page<ProductDTO> expectedStoreDTOStock = new PageImpl<>(new ArrayList<>(expectedStoreDTO.getProducts()));
        assertThat(foundStock.isEmpty(), is(expectedStoreDTOStock.isEmpty()));
    }

    @Test
    void whenFindStoreStockByInvalidStoreIdThrowNotFoundException() {
        // Given
        StoreDTO expectedStoreDTO = StoreDTOBuilder.builder().build().toStoreDTO();

        // When
        when(storeRepository.findById(INVALID_STORE_ID))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(StoreNotFoundException.class,
                () -> storeService.findStock(INVALID_STORE_ID));
    }

    @Test
    void whenAddNewProductInStockReturnProductSaved() throws StoreNotFoundException, ProductNotFoundException {
        // Give
/*        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        StoreDTO expectedStoreDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        Store storeSaved = storeMapper.toEntity(expectedStoreDTO);

        // When
        doReturn(Optional.of(storeSaved))
                .when(storeRepository).findById(VALID_STORE_ID);

        // Then
        ProductDTO productDTOAdded = storeService.addStockProduct(VALID_STORE_ID, expectedProductDTO);
        assertThat(productDTOAdded.getEan(), is(expectedProductDTO.getEan()));*/
    }

    @Test
    void whenRemoveProductFromStockReturnProductRemoved() throws StoreNotFoundException, ProductNotFoundException {
        // Give
        ProductDTO expectedProductDTO = ProductDTOBuilder.builder().build().toProductDTO();
        StoreDTO expectedStoreDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        Set<ProductDTO> emptyStock = Set.copyOf(expectedStoreDTO.getProducts());
        expectedStoreDTO.getProducts().add(expectedProductDTO);
        Store storeSaved = storeMapper.toEntity(expectedStoreDTO);

        // When
        doReturn(Optional.of(storeSaved))
                .when(storeRepository).findById(VALID_STORE_ID);

        // Then
        ProductDTO productDTORemoved = storeService.removeStockProduct(VALID_STORE_ID, expectedProductDTO);
        assertThat(productDTORemoved.getEan(), is(expectedProductDTO.getEan()));
    }

    @Test
    void whenFindProductsToExpireInStockReturnProductsPage() throws StoreNotFoundException, ProductNotFoundException {
        // Give
        StoreDTO storeDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        ProductDTO expectedProductInPag = ProductDTOBuilder.builder().build().toProductDTO();
        storeDTO.getProducts().add(expectedProductInPag);
        Store storeSaved = storeMapper.toEntity(storeDTO);
        Page<ProductDTO> expectedPage = new PageImpl<>(new ArrayList<>(storeDTO.getProducts()));

        // When
        when(storeRepository.findById(VALID_STORE_ID))
                .thenReturn(Optional.of(storeSaved));

        // Then
        Page<ProductDTO> foundPage = storeService.findProductsToExpire(VALID_STORE_ID);
        assertThat(foundPage, is(expectedPage));
        assertThat(foundPage.getContent(), equalTo(expectedPage.getContent()));
    }

    @Test
    void whenFindProductsToExpireInStockReturnEmptyProductsPage() throws StoreNotFoundException, ProductNotFoundException {
        // Give
        StoreDTO storeDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        ProductDTO expectedProductInPag = ProductDTOBuilder.builder().build().toProductDTO();
        expectedProductInPag.setExpirationDate(LocalDate.now().plus(16, ChronoUnit.DAYS));
        storeDTO.getProducts().add(expectedProductInPag);
        Store storeSaved = storeMapper.toEntity(storeDTO);
        Page<ProductDTO> expectedPage = new PageImpl<>(new ArrayList<>());

        // When
        when(storeRepository.findById(VALID_STORE_ID))
                .thenReturn(Optional.of(storeSaved));

        // Then
        Page<ProductDTO> foundPage = storeService.findProductsToExpire(VALID_STORE_ID);
        assertThat(foundPage.isEmpty(), is(expectedPage.isEmpty()));
    }

    @Test
    void whenFindProductsToExpireInStockThrowNotFoundException() throws StoreNotFoundException, ProductNotFoundException {
        // Give
        StoreDTO storeDTO = StoreDTOBuilder.builder().build().toStoreDTO();

        // When
        when(storeRepository.findById(INVALID_STORE_ID))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(StoreNotFoundException.class,
                () -> storeService.findProductsToExpire(INVALID_STORE_ID));
    }
}
