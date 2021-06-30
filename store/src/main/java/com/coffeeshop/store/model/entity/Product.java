package com.coffeeshop.store.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TB_PRODUCTS")
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 30)
    private String productName;

    @Column(name = "PRODUCT_DESCRIPTION", nullable = false, length = 150)
    private String description;

    @Column(name = "PRODUCT_EXPIRATION", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "PRODUCT_EAN", unique = true, length = 15)
    private String ean;

    @Column(name = "PRODUCT_IN_STOCK")
    private Double inStock;

    @Column(name = "PRODUCT_UNIT")
    private String unit;

    @Column(name = "PRODUCT_IS_INACTVE", nullable = false)
    private Boolean productIsInactive;
}
