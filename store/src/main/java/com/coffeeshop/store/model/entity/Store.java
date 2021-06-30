package com.coffeeshop.store.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TB_STORES")
@NoArgsConstructor
@AllArgsConstructor
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "STORE_NAME", nullable = false, unique = true, length = 30)
    private String storeName;

    @Column(name = "STORE_IS_INACTIVE")
    private Boolean storeIsInactive;

    @ManyToMany
    @JoinTable(name = "TB_STOCK",
            joinColumns = @JoinColumn(name = "STORE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private final Set<Product> products = new HashSet<>();
}
