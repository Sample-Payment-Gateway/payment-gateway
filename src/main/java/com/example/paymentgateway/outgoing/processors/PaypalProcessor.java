package com.example.paymentgateway.outgoing.processors;

import com.example.paymentgateway.domain.Mapper;
import com.example.paymentgateway.outgoing.model.OutgoingRequest;
import com.example.paymentgateway.outgoing.model.SendPaymentResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

public class PaypalProcessor extends TransactionProcessor {

    private static final String PAYPAL_ENDPOINT = System.getenv("PAYPAL_ENDPOINT");
    private static final String GET_TRANSACTION_PATH = "/payment/paypal/{transactionId}";
    private static final String CANCEL_TRANSACTION_PATH = "/payment/paypal/{transactionId}/cancel";
    private static final String PROCESS_PAYMENT_PATH = "/payment/paypal/pay";

    @Override
    public TransactionProcessor buildOutgoingRequest() {
        this.outgoingRequest = OutgoingRequest.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .account(request.getPaymentInstrument().getPaypalAccount())
                .build();
        return this;
    }

    @Override
    public SendPaymentResponse process() {
        try {
            HttpPost httppost = new HttpPost(PAYPAL_ENDPOINT + PROCESS_PAYMENT_PATH);
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
                    PAYPAL_ENDPOINT + CANCEL_TRANSACTION_PATH.replace("{transactionId}", transactionId));
            return Mapper.toSendPaymentResponse(EntityUtils.toString(httpClient.execute(httpPut).getEntity()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SendPaymentResponse get(String trasnactionId) {
        try {
            HttpGet httpGet = new HttpGet(
                    PAYPAL_ENDPOINT + GET_TRANSACTION_PATH.replace("{transactionId}", trasnactionId));
            return Mapper.toSendPaymentResponse(EntityUtils.toString(httpClient.execute(httpGet).getEntity()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
