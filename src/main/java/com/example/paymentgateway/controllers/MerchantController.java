package com.example.paymentgateway.controllers;

import com.example.paymentgateway.data.MerchantRepository;
import com.example.paymentgateway.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MerchantController {

    @Autowired
    MerchantRepository merchantRepository;

    @PostMapping("/merchant/onboard")
    public MerchantResponse processCreditCardPayment(@RequestBody MerchantRequest request) {
        if(request.getMerchantId().isBlank()) {
            request.setMerchantId(UUID.randomUUID().toString());
        }

        MerchantResponse response = MerchantResponse.builder().merchantId(request.getMerchantId()).onboardingStatus(TransactionStatus.SUCCESSFUL).build();
        merchantRepository.save(response);
        return response;
    }
}
