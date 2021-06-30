package com.coffeeshop.store.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO extends RepresentationModel<StoreDTO> implements Serializable {

    private Long storeId;

    @NotNull
    @Size(min = 1, max = 30)
    private String storeName;

    @NotNull
    private Boolean storeIsInactive;

    private Set<ProductDTO> products = new HashSet<>();
}
