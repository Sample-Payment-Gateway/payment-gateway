package com.example.paymentgateway.outgoing;

import com.example.paymentgateway.domain.PaymentInstrumentType;
import com.example.paymentgateway.domain.PaymentRequest;
import com.example.paymentgateway.outgoing.model.SendPaymentResponse;
import com.example.paymentgateway.outgoing.processors.CreditCardProcessor;
import com.example.paymentgateway.outgoing.processors.PaypalProcessor;
import com.example.paymentgateway.outgoing.processors.TransactionProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestRouter {

    private PaymentRequest request;
    private PaymentInstrumentType paymentMethod;
    private String trasnactionId;

    public RequestRouter with(PaymentRequest paymentRequest) {
        RequestRouter router = new RequestRouter();
        router.request = paymentRequest;
        return router;
    }

    public RequestRouter with(String trasnactionId) {
        this.trasnactionId = trasnactionId;
        return this;
    }

    public RequestRouter and() {return this;}
    public RequestRouter then() {return this;}


    public RequestRouter resolvePaymentMethod() {
        paymentMethod = request.getPaymentInstrument().getType();
        return this;
    }

    public RequestRouter resolvePaymentMethod(String paymentMethod) {
        this.paymentMethod = PaymentInstrumentType.valueOf(paymentMethod);
        return this;
    }

    public SendPaymentResponse send() {
        TransactionProcessor processor;
        log.info("Payment method- {}", paymentMethod);
        switch (paymentMethod) {
            case CREDITCARD -> {
                log.info("routing payment to credit card service");
                processor = new CreditCardProcessor();
                return processor.withRequest(request).buildOutgoingRequest().process();
            }
            case PAYPAL -> {
                log.info("routing payment to paypal service");
                processor = new PaypalProcessor();
                return processor.withRequest(request).buildOutgoingRequest().process();
            }
            default -> {
                return null;
            }
        }
    }

    public SendPaymentResponse get() {
        TransactionProcessor processor;
        switch (paymentMethod) {
            case CREDITCARD -> {
                processor = new CreditCardProcessor();
                return processor.get(trasnactionId);
            }
            case PAYPAL -> {
                processor = new PaypalProcessor();
                return processor.get(trasnactionId);
            }
            default -> {
                return null;
            }
        }
    }

    public SendPaymentResponse cancel() {
        TransactionProcessor processor;
        switch (paymentMethod) {
            case CREDITCARD -> {
                processor = new CreditCardProcessor();
                return processor.cancel(trasnactionId);
            }
            case PAYPAL -> {
                processor = new PaypalProcessor();
                return processor.cancel(trasnactionId);
            }
            default -> {
                return null;
            }
        }
    }
}
