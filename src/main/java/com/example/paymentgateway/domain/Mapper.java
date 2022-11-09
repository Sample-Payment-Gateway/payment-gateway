package com.example.paymentgateway.domain;

import com.example.paymentgateway.outgoing.model.OutgoingRequest;
import com.example.paymentgateway.outgoing.model.SendPaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Mapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(OutgoingRequest objectToMap) {
        try {
            return objectMapper.writeValueAsString(objectToMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static SendPaymentResponse toSendPaymentResponse(String jsonString) {
        try {
            log.info("response- {}", jsonString);
            return objectMapper.readValue(jsonString, SendPaymentResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
