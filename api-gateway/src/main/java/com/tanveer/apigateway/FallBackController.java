package com.tanveer.apigateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/orderFallBack")
    public Mono<String> orderServiceFallBack(){
        return Mono.just("Order service taking too long to respond or is down.Please try again later");
    }

    @RequestMapping("/paymentFallBack")
    public Mono<String> paymentServiceFallBack(){
        return Mono.just("Payment service taking too long to respond or is down.Please try again later");
    }
    @RequestMapping("/identityFallBack")
    public Mono<String> identityServiceFallBack(){
        return Mono.just("Identity service taking too long to respond or is down.Please try again later");
    }
    @RequestMapping("/productFallBack")
    public Mono<String> productServiceFallBack(){
        return Mono.just("Product service taking too long to respond or is down.Please try again later");
    }
}
