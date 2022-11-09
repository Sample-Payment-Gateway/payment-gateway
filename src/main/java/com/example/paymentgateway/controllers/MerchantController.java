package com.example.paymentgateway.controllers;

import com.example.paymentgateway.data.MerchantRepository;
import com.example.paymentgateway.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class MerchantController {

    @Autowired
    MerchantRepository merchantRepository;

    @PostMapping("/merchant/onboard")
    public MerchantResponse processCreditCardPayment(@RequestBody MerchantRequest request) {
        log.info("Onboarding merchant...");
        if(request.getMerchantId().isBlank()) {
            request.setMerchantId(UUID.randomUUID().toString());
        }

        MerchantResponse response = MerchantResponse.builder().merchantId(request.getMerchantId()).onboardingStatus(TransactionStatus.SUCCESSFUL).build();
        merchantRepository.save(response);
        log.info("Merchant onboarding successful. Merchant id-{}", response.getMerchantId());
        return response;
    }
}
