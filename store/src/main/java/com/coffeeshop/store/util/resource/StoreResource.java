package com.coffeeshop.store.util.resource;

import com.coffeeshop.store.controller.ProductController;
import com.coffeeshop.store.controller.response.product.ProductListResponse;
import com.coffeeshop.store.controller.response.product.ProductResponse;
import com.coffeeshop.store.model.dto.ProductDTO;
import com.coffeeshop.store.util.exception.ProductInStockIsGreaterThenZeroException;
import com.coffeeshop.store.util.exception.ProductInStockIsLowerThanZero;
import com.coffeeshop.store.util.exception.ProductInactiveCantBeIncrementedException;
import com.coffeeshop.store.util.exception.ProductNotFoundException;
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

    public ProductListResponse createLink(ProductDTO productDTO) throws ProductNotFoundException, ProductInStockIsGreaterThenZeroException, ProductInStockIsLowerThanZero, ProductInactiveCantBeIncrementedException {
        ProductListResponse productListResponse = objectMapper.convertValue(productDTO, ProductListResponse.class);
        Link link = linkTo(methodOn(ProductController.class).findByEan(productDTO.getEan())).withSelfRel();
        productListResponse.add(link);
        return productListResponse;
    }

    public ProductResponse createLinkDetail(ProductDTO productDTO) throws ProductInStockIsGreaterThenZeroException, ProductNotFoundException, ProductInactiveCantBeIncrementedException, ProductInStockIsLowerThanZero {
        ProductResponse productResponse = objectMapper.convertValue(productDTO, ProductResponse.class);
        if (productDTO.getProductIsInactive()) {
            Link link = linkTo(methodOn(ProductController.class)
                    .activeProduct(productDTO))
                    .withRel("active")
                    .withTitle("Activate product")
                    .withType("put");
            productResponse.add(link);
        }
        else if (!productDTO.getProductIsInactive() && productDTO.getInStock() == 0) {
            Link link = linkTo(methodOn(ProductController.class)
                    .inactiveProduct(productDTO))
                    .withRel("inactive")
                    .withTitle("Inactivate product")
                    .withType("put");
            productResponse.add(link);
        }
        if(!productDTO.getProductIsInactive()){
            Link link = linkTo(methodOn(ProductController.class)
                    .incrementProduct(null, null))
                    .withRel("increment")
                    .withTitle("Increment product in stock")
                    .withType("put");
            productResponse.add(link);
        }
        if(!productDTO.getProductIsInactive() && productDTO.getInStock() > 0){
            Link link = linkTo(methodOn(ProductController.class)
                    .decrementProduct(null, null))
                    .withRel("decrement")
                    .withTitle("Decrement product in stock")
                    .withType("put");
            productResponse.add(link);
        }
        return productResponse;
    }
}
