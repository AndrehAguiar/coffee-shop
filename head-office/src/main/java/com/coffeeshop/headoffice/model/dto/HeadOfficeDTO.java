package com.coffeeshop.headoffice.model.dto;

import com.coffeeshop.headoffice.model.entity.HeadOffice;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class HeadOfficeDTO extends RepresentationModel<HeadOfficeDTO> {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    private Boolean isInactive;

    private List<StoreDTO> stores;

    public HeadOfficeDTO() {
    }

    public HeadOfficeDTO(Long id, String name, Boolean isInactive, List<StoreDTO> stores) {
        this.id = id;
        this.name = name;
        this.isInactive = isInactive;
        this.stores = stores;
    }

    public HeadOfficeDTO(HeadOffice entity) {
        id = entity.getId();
        name = entity.getName();
        isInactive = entity.getIsInactive();
        stores = entity.getStores();

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

    public void setIsInactive(Boolean inactive) {
        isInactive = inactive;
    }

    public List<StoreDTO> getStores() {
        if (stores == null) {
            stores = new ArrayList<>();
        }
        return stores;
    }

    @Override
    public String toString() {
        return "HeadOfficeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isInactive=" + isInactive +
                ", stores=" + stores +
                '}';
    }
}
