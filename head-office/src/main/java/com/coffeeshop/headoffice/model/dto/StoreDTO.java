package com.coffeeshop.headoffice.model.dto;

import com.coffeeshop.headoffice.enums.Category;
import com.coffeeshop.headoffice.model.entity.Store;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StoreDTO extends RepresentationModel<StoreDTO> {

    private Long id;

    @NotNull
    private String category;

    @NotNull
    private Boolean isInactive;

    private HeadOfficeDTO headoffice;

    public StoreDTO() {
    }

    public StoreDTO(Long id, String category, Boolean isInactive, HeadOfficeDTO headoffice) {
        this.id = id;
        this.category = category;
        this.isInactive = isInactive;
        this.headoffice = headoffice;
    }

    public StoreDTO(Store entity) {
        id = entity.getId();
        category = entity.getCategory();
        isInactive = entity.getInactive();
        headoffice = new HeadOfficeDTO(entity.getHeadoffice());
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

    public HeadOfficeDTO getHeadoffice() {
        return headoffice;
    }

    public void setHeadoffice(HeadOfficeDTO headoffice) {
        this.headoffice = headoffice;
    }

    @Override
    public String toString() {
        return "StoreDTO{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", isInactive=" + isInactive +
                ", headOffice=" + headoffice +
                '}';
    }
}
