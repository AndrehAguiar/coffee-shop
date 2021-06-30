package com.coffeeshop.store.controllerTest;

import com.coffeeshop.store.builder.ProductDTOBuilder;
import com.coffeeshop.store.builder.StoreDTOBuilder;
import com.coffeeshop.store.controller.StoreController;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.dto.StoreDTO;
import com.coffeeshop.store.util.exception.StoreNotFoundException;
import com.coffeeshop.store.util.mapper.StoreMapper;
import com.coffeeshop.store.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.Set;

import static com.coffeeshop.store.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StoreControllerTest {

    private static final String STORE_API_URL_PATH = "/api/v1/store";
    private static final String STORE_API_URL_SUBPATH_GET_STOCK = "/stock";
    private static final String STORE_API_URL_SUBPATH_ADD_STOCK = "/add-product";
    private static final String STORE_API_URL_SUBPATH_REMOVE_STOCK = "/remove-product";
    private static final String STORE_API_URL_SUBPATH_TO_EXPIRE_STOCK = "/to-expire";

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 0L;

    private MockMvc mockMvc;

    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreController storeController;

    private final StoreMapper storeMapper = StoreMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(storeController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenGETStoreStockOkStatusReturned() throws Exception {
        // Given
        StoreDTO storeDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        ProductDTO productDTOInStock = ProductDTOBuilder.builder().build().toProductDTO();
        Set<ProductDTO> stock = storeDTO.getProducts();
        stock.add(productDTOInStock);
        storeDTO.setProducts(stock);
        Page<ProductDTO> stockPage = new PageImpl<>(new ArrayList<>(storeDTO.getProducts()));

        // When
        doReturn(stockPage).when(storeService).findStock(VALID_ID);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + VALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productName", is(stockPage.getContent().get(0).getProductName())))
                .andExpect(jsonPath("$.content[0].description", is(stockPage.getContent().get(0).getDescription())))
                .andExpect(jsonPath("$.content[0].expirationDate", is(stockPage.getContent().get(0).getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].ean", is(stockPage.getContent().get(0).getEan())))
                .andExpect(jsonPath("$.content[0].inStock", is(stockPage.getContent().get(0).getInStock())))
                .andExpect(jsonPath("$.content[0].unit", is(stockPage.getContent().get(0).getUnit())))
                .andExpect(jsonPath("$.content[0].productIsInactive", is(stockPage.getContent().get(0).getProductIsInactive())));
    }

    @Test
    void whenGETStoreStockWithInvalidIdNotFoundStatusReturned() throws Exception {
        // Given
        StoreDTO storeDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        ProductDTO productDTOInStock = ProductDTOBuilder.builder().build().toProductDTO();
        Set<ProductDTO> stock = storeDTO.getProducts();
        stock.add(productDTOInStock);
        storeDTO.setProducts(stock);

        // When
        doThrow(StoreNotFoundException.class)
                .when(storeService).findStock(INVALID_ID);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTStoreProductToStockOkStatusReturned() throws Exception {
        // Given
        /*ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(storeService.addStockProduct(VALID_ID, productDTO))
                .thenReturn(productDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + VALID_ID + STORE_API_URL_SUBPATH_ADD_STOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is(productDTO.getProductName())))
                .andExpect(jsonPath("$.description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$.expirationDate", is(productDTO.getExpirationDate().toString())))
                .andExpect(jsonPath("$.ean", is(productDTO.getEan())))
                .andExpect(jsonPath("$.inStock", is(productDTO.getInStock())))
                .andExpect(jsonPath("$.unit", is(productDTO.getUnit())))
                .andExpect(jsonPath("$.productIsInactive", is(productDTO.getProductIsInactive())));*/


    }

    @Test
    void whenPUTInvalidIdStoreProductToStockNotFoundStatusReturned() throws Exception {
        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(storeService.addStockProduct(INVALID_ID, productDTO))
                .thenThrow(StoreNotFoundException.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + INVALID_ID + STORE_API_URL_SUBPATH_ADD_STOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTStoreInvalidProductToStockBadRequestStatusReturned() throws Exception {
        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setProductId(null);
        productDTO.setEan(null);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + VALID_ID + STORE_API_URL_SUBPATH_ADD_STOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTStoreProductFromStockOkStatusReturned() throws Exception {
        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(storeService.removeStockProduct(VALID_ID, productDTO))
                .thenReturn(productDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + VALID_ID + STORE_API_URL_SUBPATH_REMOVE_STOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is(productDTO.getProductName())))
                .andExpect(jsonPath("$.description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$.expirationDate", is(productDTO.getExpirationDate().toString())))
                .andExpect(jsonPath("$.ean", is(productDTO.getEan())))
                .andExpect(jsonPath("$.inStock", is(productDTO.getInStock())))
                .andExpect(jsonPath("$.unit", is(productDTO.getUnit())))
                .andExpect(jsonPath("$.productIsInactive", is(productDTO.getProductIsInactive())));


    }

    @Test
    void whenPUTInvalidIdStoreProductFromStockOkStatusReturned() throws Exception {
        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(storeService.removeStockProduct(INVALID_ID, productDTO))
                .thenThrow(StoreNotFoundException.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + INVALID_ID + STORE_API_URL_SUBPATH_REMOVE_STOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTStoreInvalidProductFromStockOkStatusReturned() throws Exception {
        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setProductId(null);
        productDTO.setEan(null);

        // When

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + INVALID_ID + STORE_API_URL_SUBPATH_REMOVE_STOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETProductToExpireInStoreStockOkStatusReturned() throws Exception {
        // Given
        StoreDTO storeDTO = StoreDTOBuilder.builder().build().toStoreDTO();
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        Set<ProductDTO> stock = storeDTO.getProducts();
        stock.add(productDTO);
        storeDTO.setProducts(stock);
        Page<ProductDTO> stockPage = new PageImpl<>(new ArrayList<>(storeDTO.getProducts()));

        // When
        doReturn(stockPage).when(storeService).findProductsToExpire(VALID_ID);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + VALID_ID + STORE_API_URL_SUBPATH_TO_EXPIRE_STOCK)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productName", is(stockPage.getContent().get(0).getProductName())))
                .andExpect(jsonPath("$.content[0].description", is(stockPage.getContent().get(0).getDescription())))
                .andExpect(jsonPath("$.content[0].expirationDate", is(stockPage.getContent().get(0).getExpirationDate().toString())))
                .andExpect(jsonPath("$.content[0].ean", is(stockPage.getContent().get(0).getEan())))
                .andExpect(jsonPath("$.content[0].inStock", is(stockPage.getContent().get(0).getInStock())))
                .andExpect(jsonPath("$.content[0].unit", is(stockPage.getContent().get(0).getUnit())))
                .andExpect(jsonPath("$.content[0].productIsInactive", is(stockPage.getContent().get(0).getProductIsInactive())));
    }

    @Test
    void whenGETProductToExpireInStoreStockByInvalidIdNotFoundStatusReturned() throws Exception {
        // Given
        StoreDTO storeDTO = StoreDTOBuilder.builder().build().toStoreDTO();

        // When
        when(storeService.findProductsToExpire(INVALID_ID))
                .thenThrow(StoreNotFoundException.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(STORE_API_URL_PATH + STORE_API_URL_SUBPATH_GET_STOCK +
                "/" + INVALID_ID + STORE_API_URL_SUBPATH_TO_EXPIRE_STOCK)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
