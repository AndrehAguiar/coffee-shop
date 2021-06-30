package com.coffeeshop.store.builder;

import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.dto.StoreDTO;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public class StoreDTOBuilder {

    @Builder.Default
    private final Long storeId = 1L;

    @Builder.Default
    private final String storeName = "Loja BH";

    @Builder.Default
    private final Boolean storeIsInactive = false;

    @Builder.Default
    private final Set<ProductDTO> products = new HashSet<>();

    public StoreDTO toStoreDTO() {
        return new StoreDTO(storeId,
                storeName,
                storeIsInactive,
                products);
    }
}
