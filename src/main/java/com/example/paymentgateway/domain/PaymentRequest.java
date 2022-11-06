package com.example.paymentgateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentRequest {
    String merchantId;
    BigDecimal amount;
    String currency;
    PaymentInstrument paymentInstrument;

    public boolean hasValidMerchant() {
        return !this.merchantId.isBlank();
    }

    public boolean hasValidAmount() {
        return (null != this.amount) && (!this.amount.equals(BigDecimal.ZERO));
    }

    public boolean hasValidCurrency() {
        return !this.currency.isBlank();
    }

    public boolean hasValidPaymentInstrument() {
        return this.paymentInstrument.isValid();
    }
}
