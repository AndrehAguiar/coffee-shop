package com.coffeeshop.store.util.mapper;

import com.coffeeshop.store.model.dto.StoreDTO;
import com.coffeeshop.store.model.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoreMapper {

    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    Store toEntity(StoreDTO storeDTO);

    StoreDTO toDTO(Store store);
}
