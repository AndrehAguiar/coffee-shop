package com.coffeeshop.headoffice.controller.response;

import com.coffeeshop.headoffice.model.dto.HeadOfficeDTO;
import com.coffeeshop.headoffice.model.dto.StoreDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class StoreResponse extends RepresentationModel<StoreDTO> {

    private Long storeId;
    private String category;
    private Boolean isInactive;
    private HeadOfficeDTO headoffice;

    public StoreResponse() {
    }

    public StoreResponse(Long storeId, String category, Boolean isInactive, HeadOfficeDTO headoffice) {
        this.storeId = storeId;
        this.category = category;
        this.isInactive = isInactive;
        this.headoffice = headoffice;
    }

    @JsonProperty("id")
    public Long getStoreId() {
        return storeId;
    }

    @JsonProperty("id")
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
}
