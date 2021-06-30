package com.coffeeshop.headoffice.controller.response;

import com.coffeeshop.headoffice.model.dto.HeadOfficeDTO;
import com.coffeeshop.headoffice.model.entity.Store;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;

public class HeadOfficeResponse extends RepresentationModel<HeadOfficeDTO> {

    private Long id;
    private String name;
    private Boolean isInactive;
    private final Set<Store> stores = new HashSet<>();

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
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

    public Set<Store> getStores() {
        return stores;
    }
}
