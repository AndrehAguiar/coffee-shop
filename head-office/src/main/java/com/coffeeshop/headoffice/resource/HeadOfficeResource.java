package com.coffeeshop.headoffice.resource;

import com.coffeeshop.headoffice.controller.HeadOfficeController;
import com.coffeeshop.headoffice.controller.response.HeadOfficeResponse;
import com.coffeeshop.headoffice.model.dto.HeadOfficeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HeadOfficeResource {

    private final ObjectMapper objectMapper;

    public HeadOfficeResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public HeadOfficeResponse createLinkDetail(HeadOfficeDTO headOfficeDTO) {
        HeadOfficeResponse headOfficeResponse = objectMapper.convertValue(headOfficeDTO, HeadOfficeResponse.class);
        Link link;
        if (headOfficeDTO.getIsInactive()) {
            link = linkTo(methodOn(HeadOfficeController.class)
                    .activate(headOfficeDTO.getId()))
                    .withRel("activate")
                    .withTitle("Activate Head Office")
                    .withType("put");
        } else {
            link = linkTo(methodOn(HeadOfficeController.class)
                    .inactivate(headOfficeDTO.getId()))
                    .withRel("inactivate")
                    .withTitle("Inactivate Head Office")
                    .withType("put");
        }
        headOfficeResponse.add(link);
        return headOfficeResponse;
    }
}
