package com.tanveer.productservice.infrustructure.config;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.application.ProductServiceImpl;
import com.tanveer.productservice.domain.ProductEvent;
import com.tanveer.productservice.domain.ProductService;
import com.tanveer.productservice.infrustructure.persistence.ProductJpaRepository;
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
