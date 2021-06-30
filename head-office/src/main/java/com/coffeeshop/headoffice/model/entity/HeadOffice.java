package com.coffeeshop.headoffice.model.entity;

import com.coffeeshop.headoffice.model.dto.HeadOfficeDTO;
import com.coffeeshop.headoffice.model.dto.StoreDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "TB_HEAD_OFFICES")
public class HeadOffice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HEAD_OFFICE_ID")
    private Long id;

    @Column(name = "HEAD_OFFICE_NAME", unique = true, length = 30, nullable = false)
    private String name;

    @Column(name = "HEAD_OFFICE_IS_INACTIVE", nullable = false)
    private Boolean isInactive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "headoffice")
    private List<Store> stores;

    public HeadOffice() {
    }

    public HeadOffice(String name, Boolean isInactive, List<Store> stores) {
        this.name = name;
        this.isInactive = isInactive;
        this.stores = stores;
    }

    public HeadOffice(Long id, String name, Boolean isInactive, List<Store> stores) {
        this.id = id;
        this.name = name;
        this.isInactive = isInactive;
        this.stores = stores;
    }

    public HeadOffice(HeadOfficeDTO dto) {
        id = dto.getId();
        name = dto.getName();
        isInactive = dto.getIsInactive();
        stores = dto.getStores()
                .stream().map(Store::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsInactive() {
        return isInactive;
    }

    public void setIsInactive(Boolean isInactive) {
        this.isInactive = isInactive;
    }

    public List<StoreDTO> getStores() {
        if (stores == null) {
            stores = new ArrayList<>();
        }
        return stores.stream().map(StoreDTO::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "HeadOffice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isInactive=" + isInactive +
                ", stores=" + stores +
                '}';
    }
}
