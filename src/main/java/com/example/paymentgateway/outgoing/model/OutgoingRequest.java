package com.example.paymentgateway.outgoing.model;

import com.example.paymentgateway.domain.CreditCard;
import com.example.paymentgateway.domain.PaypalAccount;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OutgoingRequest {
    CreditCard card;
    PaypalAccount account;
    BigDecimal amount;
    String currency;
}
