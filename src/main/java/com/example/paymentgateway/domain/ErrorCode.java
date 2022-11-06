package com.example.paymentgateway.domain;

public enum ErrorCode {
    INVALID_PAYMENT_IINSTRUMENT(40010, "The payment instrument is invalid. Please reverify"),
    INVALID_MERCHANT_ID(40410, "The merchant not enrolled. Please enroll with us before using the API"),
    MISSING_MANDATORY_INFORMATION(40040, "Mandatory fields missing. Can not process the payment");

    final int errorCode;
    final String errorMessage;
    ErrorCode(int code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }
}
