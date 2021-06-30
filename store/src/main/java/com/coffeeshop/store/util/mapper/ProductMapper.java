package com.coffeeshop.store.util.mapper;

import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductDTO productDTO);

    ProductDTO toDTO(Product product);
}
