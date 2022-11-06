package com.example.paymentgateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Trasnaction {
    String merchantId;
    String transactionId;
    PaymentInstrumentType instrumentType;
    TransactionStatus transactionStatus;
    ErrorDetails errorDetails;
}
