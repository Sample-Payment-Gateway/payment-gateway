package com.example.paymentgateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditCard {
    String cardNumber;
    String expiryMonth;
    String expiryYear;
    String cvc;
    String brand;
    String accountHolderName;
}
