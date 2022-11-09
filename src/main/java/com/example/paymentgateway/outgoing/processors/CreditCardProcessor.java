package com.example.paymentgateway.outgoing.processors;

import com.example.paymentgateway.domain.Mapper;
import com.example.paymentgateway.outgoing.model.OutgoingRequest;
import com.example.paymentgateway.outgoing.model.SendPaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

@Slf4j
public class CreditCardProcessor extends TransactionProcessor {

    private static final String CREDIT_CARD_ENDPOINT = System.getenv("CREDIT_CARD_ENDPOINT");
    private static final String GET_TRANSACTION_PATH = "/payment/creditcard/{transactionId}";
    private static final String CANCEL_TRANSACTION_PATH = "/payment/creditcard/{transactionId}/cancel";
    private static final String PROCESS_PAYMENT_PATH = "/payment/creditcard/pay";

    @Override
    public TransactionProcessor buildOutgoingRequest() {
        this.outgoingRequest = OutgoingRequest.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .card(request.getPaymentInstrument().getCard())
                .build();
        return this;
    }

    @Override
    public SendPaymentResponse process() {
        try {
            log.info("External call -  {}", CREDIT_CARD_ENDPOINT + PROCESS_PAYMENT_PATH);
            log.info("request- {}", Mapper.toJson(outgoingRequest));
            HttpPost httppost = new HttpPost(CREDIT_CARD_ENDPOINT + PROCESS_PAYMENT_PATH);
            StringEntity stringEntity = new StringEntity(Mapper.toJson(outgoingRequest));
            httppost.setEntity(stringEntity);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            return Mapper.toSendPaymentResponse(EntityUtils.toString(httpClient.execute(httppost).getEntity()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SendPaymentResponse cancel(String transactionId) {
        try {
            HttpPut httpPut = new HttpPut(
                    CREDIT_CARD_ENDPOINT + CANCEL_TRANSACTION_PATH.replace("{transactionId}", transactionId));
            return Mapper.toSendPaymentResponse(EntityUtils.toString(httpClient.execute(httpPut).getEntity()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SendPaymentResponse get(String trasnactionId) {
        try {
            HttpGet httpGet = new HttpGet(
                    CREDIT_CARD_ENDPOINT + GET_TRANSACTION_PATH.replace("{transactionId}", trasnactionId));
            return Mapper.toSendPaymentResponse(EntityUtils.toString(httpClient.execute(httpGet).getEntity()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
