package com.tanveer.productservice.infrastructure.config;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.application.ProductServiceImpl;
import com.tanveer.productservice.domain.ProductEvent;
import com.tanveer.productservice.domain.ProductService;
import com.tanveer.productservice.infrastructure.persistence.ProductJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ProductService getProductServiceImpl(ProductJpaRepository repository,
                                                EventRepository<ProductEvent> eventRepository){
        return new ProductServiceImpl(repository,eventRepository);
    }
}
