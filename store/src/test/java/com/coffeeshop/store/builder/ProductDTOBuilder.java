package com.coffeeshop.store.builder;

import com.coffeeshop.store.model.dto.ProductDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class ProductDTOBuilder {

    @Builder.Default
    private final Long productId = 1L;

    @Builder.Default
    private final String productName = "Batata";

    @Builder.Default
    private final String description = "Frita";

    @Builder.Default
    private final LocalDate expirationDate = LocalDate.now();

    @Builder.Default
    private final String ean = "12345678910";

    @Builder.Default
    private final Double inStock = 20D;

    @Builder.Default
    private final String unit = "kg";

    @Builder.Default
    private final Boolean productIsInactive = false;

    public ProductDTO toProductDTO(){
        return new ProductDTO(productId,
                productName,
                description,
                expirationDate,
                ean,
                inStock,
                unit,
                productIsInactive);
    }
}
