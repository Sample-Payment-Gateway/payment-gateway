package com.example.paymentgateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentInstrument {
    PaymentInstrumentType type;
    CreditCard card;
    PaypalAccount paypalAccount;

    public boolean isValid() {
        switch (type) {
            case CREDITCARD -> {
                return null != card;
            }
            case PAYPAL -> {
                return null != paypalAccount;
            }
            default -> {
                return false;
            }
        }
    }
}

