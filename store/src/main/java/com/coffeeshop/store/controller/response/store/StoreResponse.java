package com.coffeeshop.store.controller.response.store;

import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.dto.StoreDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;

public class StoreResponse extends RepresentationModel<StoreDTO> {

    private Long storeId;
    private String storeName;
    private Boolean storeIsInactive;
    private Set<ProductDTO> products = new HashSet<>();

    @JsonProperty("storeId")
    public Long getStoreId() {
        return storeId;
    }

    @JsonProperty("storeId")
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Boolean getStoreIsInactive() {
        return storeIsInactive;
    }

    public void setStoreIsInactive(Boolean storeIsInactive) {
        this.storeIsInactive = storeIsInactive;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }
}
