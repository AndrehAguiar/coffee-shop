package com.coffeeshop.store.util.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jackson {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // PRORPIEDADES NÃO MAPEADAS NÃO QUEBRAM
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // FALHA SE ALGUMA PRORPIEDADE ESTIVER VAZIA
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // SERVE PARA COMPATIBILIDADE DE ARRAYS
        // QUANDO TEM UM ARRAY COM UM ITEM
        // CASO NÃO TENHA ESSA CONFIG ELE SE PERDE
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        // SERIALIZE DATAS
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
