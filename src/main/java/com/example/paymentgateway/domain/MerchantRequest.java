package com.example.paymentgateway.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class MerchantRequest {
    String merchantName;
    String merchantId;
    String description;
}
