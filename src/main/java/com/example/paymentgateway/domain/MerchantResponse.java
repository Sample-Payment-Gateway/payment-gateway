package com.example.paymentgateway.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class MerchantResponse {
    @Id
    String merchantId;
    TransactionStatus onboardingStatus;
}
