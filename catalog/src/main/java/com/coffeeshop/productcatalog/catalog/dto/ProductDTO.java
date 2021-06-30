package com.coffeeshop.productcatalog.catalog.dto;

import com.coffeeshop.productcatalog.catalog.entity.Product;

import java.io.Serializable;
import java.time.LocalDate;

public class ProductDTO implements Serializable {
    private Long productId;
    private String productName;
    private String description;
    private String ean;
    private Boolean productIsInactive;

    public ProductDTO() {
    }

    public ProductDTO(String productName, String description, LocalDate expirationDate, String ean, Boolean productIsInactive) {
        this.productName = productName;
        this.description = description;
        this.ean = ean;
        this.productIsInactive = productIsInactive;
    }

    public ProductDTO(Product entity) {
        productId = entity.getProductId();
        productName = entity.getProductName();
        description = entity.getDescription();
        ean = entity.getEan();
        productIsInactive = entity.getProductIsInactive();
    }

    public Long getProductId() {
        return productId;
    }

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

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Boolean getProductIsInactive() {
        return productIsInactive;
    }

    public void setProductIsInactive(Boolean productIsInactive) {
        this.productIsInactive = productIsInactive;
    }

    @Override
    public String toString() {
        return "{" +
                "productId:" + productId +
                ", productName:" + productName +
                ", description:" + description +
                ", ean:" + ean +
                ", productIsInactive:" + productIsInactive +
                '}';
    }
}
