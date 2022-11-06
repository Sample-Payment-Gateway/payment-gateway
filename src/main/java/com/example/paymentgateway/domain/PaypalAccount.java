package com.example.paymentgateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaypalAccount {
    String username;
    String password;
    String accountHolderName;
}
