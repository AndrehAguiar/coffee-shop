package com.coffeeshop.headoffice.resource;

import com.coffeeshop.headoffice.controller.StoreController;
import com.coffeeshop.headoffice.controller.response.StoreResponse;
import com.coffeeshop.headoffice.model.dto.StoreDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreResource {

    private final ObjectMapper objectMapper;

    public StoreResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public StoreResponse createLinkDetail(StoreDTO storeDTO) {
        StoreResponse storeResponse = objectMapper.convertValue(storeDTO, StoreResponse.class);
        Link link;
        if (storeDTO.getInactive()) {
            link = linkTo(methodOn(StoreController.class)
                    .activate(storeDTO.getId()))
                    .withRel("activate")
                    .withTitle("Activate Head Office")
                    .withType("put");
        } else {
            link = linkTo(methodOn(StoreController.class)
                    .inactivate(storeDTO.getId()))
                    .withRel("inactivate")
                    .withTitle("Inactivate Head Office Store")
                    .withType("put");
        }
        storeResponse.add(link);
        return storeResponse;
    }
}
