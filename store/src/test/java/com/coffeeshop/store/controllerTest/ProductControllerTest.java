package com.coffeeshop.store.controllerTest;

import com.coffeeshop.store.builder.ProductDTOBuilder;
import com.coffeeshop.store.controller.ProductController;
import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.util.exception.ProductInStockIsGreaterThenZeroException;
import com.coffeeshop.store.util.exception.ProductInStockIsLowerThanZero;
import com.coffeeshop.store.util.exception.ProductNotFoundException;
import com.coffeeshop.store.util.mapper.ProductMapper;
import com.coffeeshop.store.util.resource.ProductResource;
import com.coffeeshop.store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.coffeeshop.store.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private static final Long VALID_PRODUCT_ID = 1L;
    private static final Long INVALID_PRODUCT_ID = 0L;

    private static final String VALID_PRODUCT_EAN = "12345678910";
    private static final String INVALID_PRODUCT_EAN = "0000000000000";

    private static final String VALID_PRODUCT_NAME = "Batata";
    private static final String INVALID_PRODUCT_NAME = "Cafe";

    private static final String PRODUCT_API_URL_PATH = "/api/v1/product";
    private static final String PRODUCT_API_GET_BYNAME_URL = "/by-name/";
    private static final String PRODUCT_API_GET_BYEAN_URL = "/by-ean/";
    private static final String PRODUCT_API_SUBPATH_INACTIVATE_URL = "/inactivate";
    private static final String PRODUCT_API_SUBPATH_ACTIVATE_URL = "/activate";
    private static final String PRODUCT_API_SUBPATH_INCREMENT_URL = "/increment/";
    private static final String PRODUCT_API_SUBPATH_DECREMENT_URL = "/decrement/";
    private static final String PRODUCT_API_SUBPATH_FINDALL_URL = "/all";
    private static final String PRODUCT_API_SUBPATH_FINDALL_ACTIVE_URL = "/active";

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductResource productResource;

    @InjectMocks
    private ProductController productController;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTNewProductToCreateCreatedStatusIsReturned() throws Exception {
        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(productService.createProduct(productDTO))
                .thenReturn(productDTO);

        // Then
        mockMvc.perform(post(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName", is(productDTO.getProductName())))
                .andExpect(jsonPath("$.description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$.expirationDate", is(productDTO.getExpirationDate().toString())))
                .andExpect(jsonPath("$.ean", is(productDTO.getEan())))
                .andExpect(jsonPath("$.inStock", is(productDTO.getInStock())))
                .andExpect(jsonPath("$.unit", is(productDTO.getUnit())))
                .andExpect(jsonPath("$.productIsInactive", is(productDTO.getProductIsInactive())));
    }

    @Test
    void whenPOSTNewProductWithOutRequiredFieldBadRequestStatusIsReturned() throws Exception {
        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setProductName(null);

        // Then
        mockMvc.perform(post(PRODUCT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETProductByNameOkStatusIsReturned() throws Exception {
        // Given
/*        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(productService.findByName(productDTO.getProductName()))
                .thenReturn(Collections.singletonList(productDTO));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + PRODUCT_API_GET_BYNAME_URL + VALID_PRODUCT_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName", is(productDTO.getProductName())))
                .andExpect(jsonPath("$[0].description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$[0].expirationDate", is(productDTO.getExpirationDate().toString())))
                .andExpect(jsonPath("$[0].ean", is(productDTO.getEan())))
                .andExpect(jsonPath("$[0].inStock", is(productDTO.getInStock())))
                .andExpect(jsonPath("$[0].unit", is(productDTO.getUnit())))
                .andExpect(jsonPath("$[0].productIsInactive", is(productDTO.getProductIsInactive())));*/
    }

    @Test
    void whenGETProductByUnknownNameOkStatusIsReturned() throws Exception {
/*        // Given
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(productService.findByName(INVALID_PRODUCT_NAME))
                .thenReturn(Collections.EMPTY_LIST);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + PRODUCT_API_GET_BYNAME_URL + INVALID_PRODUCT_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());*/
    }

    @Test
    void whenGETProductByEanReturnOkStatusIsReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        ProductResponse response = productResource.createLinkDetail(productDTO);

        // When
        doReturn(ResponseEntity.class)
                .when(productService).findByEan(VALID_PRODUCT_EAN);
        when(productService.findByEan(VALID_PRODUCT_EAN))
                .thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + PRODUCT_API_GET_BYEAN_URL + VALID_PRODUCT_EAN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is(productDTO.getProductName())))
                .andExpect(jsonPath("$.description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$.expirationDate", is(productDTO.getExpirationDate().toString())))
                .andExpect(jsonPath("$.ean", is(productDTO.getEan())))
                .andExpect(jsonPath("$.inStock", is(productDTO.getInStock())))
                .andExpect(jsonPath("$.unit", is(productDTO.getUnit())))
                .andExpect(jsonPath("$.productIsInactive", is(productDTO.getProductIsInactive())));
    }

    @Test
    void whenGETProductByUnknownEanNotFoundStatusIsReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(productService.findByEan(INVALID_PRODUCT_EAN))
                .thenThrow(ProductNotFoundException.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + PRODUCT_API_GET_BYEAN_URL + INVALID_PRODUCT_EAN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTIsCalledToInactivateProductOkStatusIsReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setInStock(0D);

        // When
        when(productService.inactivateProduct(productDTO, true))
                .thenReturn(productDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH + PRODUCT_API_SUBPATH_INACTIVATE_URL)
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
    void whenPUTIsCalledToActivateProductOkStatusIsReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setInStock(0D);
        productDTO.setProductIsInactive(true);

        // When
        when(productService.inactivateProduct(productDTO, false))
                .thenReturn(productDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH + PRODUCT_API_SUBPATH_ACTIVATE_URL)
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
    void whenPUTIsCalledToActivateInvalidProductOkStatusIsReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setInStock(0D);
        productDTO.setProductIsInactive(true);
        productDTO.setProductId(INVALID_PRODUCT_ID);

        // When
        when(productService.inactivateProduct(productDTO, false))
                .thenThrow(ProductNotFoundException.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH + "/activate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isNotFound());

    }

    @Test
    void whenPUTIsCalledToInactivateProductInStockGreaterThanZeroBadRequestStatusIsReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        when(productService.inactivateProduct(productDTO, true))
                .thenThrow(ProductInStockIsGreaterThenZeroException.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH + "/inactivate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTIsCalledToIncrementProductInStockOkStatusIsReturned() throws Exception {
        // Give
        Double quantityToIncrement = 10D;

        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setInStock(productDTO.getInStock() + quantityToIncrement);
        // When
        when(productService.increment(VALID_PRODUCT_ID, quantityToIncrement))
                .thenReturn(productDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH + "/" + VALID_PRODUCT_ID + PRODUCT_API_SUBPATH_INCREMENT_URL + quantityToIncrement)
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
    void whenPUTIsCalledToDecrementProductInStockOkStatusIsReturned() throws Exception {
        // Give
        Double quantityToDecrement = 10D;

        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setInStock(productDTO.getInStock() - quantityToDecrement);
        // When
        when(productService.decrement(VALID_PRODUCT_ID, quantityToDecrement))
                .thenReturn(productDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH + "/" + VALID_PRODUCT_ID + PRODUCT_API_SUBPATH_DECREMENT_URL + quantityToDecrement)
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
    void whenPUTIsCalledToDecrementProductInStockAndResultLowerThanZeroBadRequestStatusIsReturned() throws Exception {
        // Give
        Double quantityToDecrement = 100D;

        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();
        productDTO.setInStock(productDTO.getInStock() - quantityToDecrement);
        // When
        when(productService.decrement(VALID_PRODUCT_ID, quantityToDecrement))
                .thenThrow(ProductInStockIsLowerThanZero.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_API_URL_PATH + "/" + VALID_PRODUCT_ID +
                PRODUCT_API_SUBPATH_DECREMENT_URL + quantityToDecrement)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenGETAllProductsListOkStatusReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
/*        when(productService.findAll())
                .thenReturn(Collections.singletonList(productDTO));*/

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + PRODUCT_API_SUBPATH_FINDALL_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName", is(productDTO.getProductName())))
                .andExpect(jsonPath("$[0].description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$[0].expirationDate", is(productDTO.getExpirationDate().toString())))
                .andExpect(jsonPath("$[0].ean", is(productDTO.getEan())))
                .andExpect(jsonPath("$[0].inStock", is(productDTO.getInStock())))
                .andExpect(jsonPath("$[0].unit", is(productDTO.getUnit())))
                .andExpect(jsonPath("$[0].productIsInactive", is(productDTO.getProductIsInactive())));

    }

    @Test
    void whenFindAllActiveProductOkStatusReturned() throws Exception {
        // Give
        ProductDTO productDTO = ProductDTOBuilder.builder().build().toProductDTO();

        // When
        //when(productService.findAllActive())
         //       .thenReturn(Collections.singletonList(productDTO));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_API_URL_PATH + PRODUCT_API_SUBPATH_FINDALL_ACTIVE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName", is(productDTO.getProductName())))
                .andExpect(jsonPath("$[0].description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$[0].expirationDate", is(productDTO.getExpirationDate().toString())))
                .andExpect(jsonPath("$[0].ean", is(productDTO.getEan())))
                .andExpect(jsonPath("$[0].inStock", is(productDTO.getInStock())))
                .andExpect(jsonPath("$[0].unit", is(productDTO.getUnit())))
                .andExpect(jsonPath("$[0].productIsInactive", is(productDTO.getProductIsInactive())));

    }

}
