package com.example.paymentgateway.outgoing.processors;

import com.example.paymentgateway.domain.PaymentRequest;
import com.example.paymentgateway.outgoing.model.OutgoingRequest;
import com.example.paymentgateway.outgoing.model.SendPaymentResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

public abstract class TransactionProcessor {
    HttpClient httpClient = HttpClients.createDefault();
    PaymentRequest request;
    OutgoingRequest outgoingRequest;

    public TransactionProcessor withRequest(PaymentRequest request) {
        this.request = request;
        return this;
    }

    public abstract TransactionProcessor buildOutgoingRequest();
    public abstract SendPaymentResponse process();
    public abstract SendPaymentResponse cancel(String transactionId);
    public abstract SendPaymentResponse get(String transactionId);
}
