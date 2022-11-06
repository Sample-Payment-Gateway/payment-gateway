package com.example.paymentgateway.outgoing.model;

import com.example.paymentgateway.domain.ErrorDetails;
import com.example.paymentgateway.domain.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SendPaymentResponse {
    String transactionId;
    String transactionType;
    TransactionStatus transactionStatus;
    String timestamp;
    String amount;
    String currency;
    ErrorDetails errorDetails;

}
