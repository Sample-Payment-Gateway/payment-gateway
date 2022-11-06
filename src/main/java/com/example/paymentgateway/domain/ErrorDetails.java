package com.example.paymentgateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDetails {
    int errorCode;
    String errorMessage;

    public static ErrorDetails from(ErrorCode code) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(code.errorCode);
        errorDetails.setErrorMessage(code.errorMessage);
        return errorDetails;
    }
}
