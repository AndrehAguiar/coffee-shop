package com.coffeeshop.store.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends RepresentationModel<ProductDTO> implements Serializable {

    private Long productId;

    @NotNull
    @Size(min = 1, max = 30)
    private String productName;

    @NotNull
    @Size(min = 1, max = 200)
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @NotNull
    @Size(min = 1, max = 15)
    private String ean;

    @NotNull
    @Min(0)
    private Double inStock;

    @NotNull
    @Size(min = 1, max = 2)
    private String unit;

    @NotNull
    private Boolean productIsInactive;
}
