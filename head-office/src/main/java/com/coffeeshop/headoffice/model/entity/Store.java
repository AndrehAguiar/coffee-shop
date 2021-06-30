package com.coffeeshop.headoffice.model.entity;

import com.coffeeshop.headoffice.enums.Category;
import com.coffeeshop.headoffice.model.dto.StoreDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "TB_STORES")
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID")
    private Long id;

    @Column(name = "STORE_CATEGORY", length = 30)
    private String category;

    @Column(name = "STORE_IS_INACTIVE")
    private Boolean isInactive;

    @ManyToOne
    @JoinColumn(name = "head_office_id")
    private HeadOffice headoffice;

    public Store() {
    }

    public Store(String category, Boolean isInactive, HeadOffice headoffice) {
        this.category = category;
        this.isInactive = isInactive;
        this.headoffice = headoffice;
    }

    public Store(Long id, String category, Boolean isInactive, HeadOffice headoffice) {
        this.id = id;
        this.category = category;
        this.isInactive = isInactive;
        this.headoffice = headoffice;
    }

    public Store(StoreDTO dto) {
        id = dto.getId();
        category = dto.getCategory();
        isInactive = dto.getInactive();
        headoffice = new HeadOffice(dto.getHeadoffice());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getInactive() {
        return isInactive;
    }

    public void setInactive(Boolean inactive) {
        isInactive = inactive;
    }

    public HeadOffice getHeadoffice() {
        return headoffice;
    }

    public void setHeadoffice(HeadOffice headoffice) {
        this.headoffice = headoffice;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", isInactive=" + isInactive +
                ", headOffice=" + headoffice +
                '}';
    }
}
