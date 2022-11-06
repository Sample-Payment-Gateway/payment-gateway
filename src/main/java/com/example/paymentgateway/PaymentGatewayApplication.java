package com.example.paymentgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class PaymentGatewayApplication {
    public static void main(String[] args) {
        SpringApplication gateway = new SpringApplication(PaymentGatewayApplication.class);
        gateway.setDefaultProperties(Collections
                .singletonMap("server.port", "8080"));
        gateway.run(args);
    }
}
