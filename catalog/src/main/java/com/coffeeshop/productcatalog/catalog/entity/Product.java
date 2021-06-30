package com.coffeeshop.productcatalog.catalog.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TB_PRODUCTS")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 30)
    private String productName;

    @Column(name = "PRODUCT_DESCRIPTION", nullable = false, length = 150)
    private String description;

    @Column(name = "PRODUCT_EAN", unique = true, length = 15)
    private String ean;

    @Column(name = "PRODUCT_IS_INACTVE", nullable = false)
    private Boolean productIsInactive;

    public Product() {
    }

    public Product(String productName, String description, LocalDate expirationDate, String ean, Boolean productIsInactive) {
        this.productName = productName;
        this.description = description;
        this.ean = ean;
        this.productIsInactive = productIsInactive;
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
