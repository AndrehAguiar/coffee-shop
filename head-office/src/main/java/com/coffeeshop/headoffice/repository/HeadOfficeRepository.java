package com.coffeeshop.headoffice.repository;

import com.coffeeshop.headoffice.model.entity.HeadOffice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface HeadOfficeRepository extends CrudRepository<HeadOffice, Long> {

    @Query("SELECT new com.coffeeshop.headoffice.model.dto.StoreDTO(obj.stores)" +
            "FROM Store AS obj WHERE obj.headoffice.id = ?1 GROUP BY obj.headoffice")
    HeadOffice findHeadOfficeWithStores(Long id);
}
