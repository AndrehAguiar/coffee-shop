package com.coffeeshop.store.controller.response.product;

import com.coffeeshop.store.model.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class ProductResponse extends RepresentationModel<ProductDTO> {

    private Long productId;
    private String productName;
    private String description;
    private LocalDate expirationDate;
    private String ean;
    private Double inStock;

    @JsonProperty("productId")
    public Long getProductId() {
        return productId;
    }

    @JsonProperty("productId")
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Double getInStock() {
        return inStock;
    }

    public void setInStock(Double inStock) {
        this.inStock = inStock;
    }
}
