package com.example.paymentgateway.controllers;

import com.example.paymentgateway.data.MerchantRepository;
import com.example.paymentgateway.domain.*;
import com.example.paymentgateway.outgoing.RequestRouter;
import com.example.paymentgateway.outgoing.model.SendPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PaymentController {

    @Autowired
    MerchantRepository merchantRepository;

    @PostMapping("/payment/pay")
    public Trasnaction processCreditCardPayment(@RequestBody PaymentRequest request) {

        Trasnaction.TrasnactionBuilder trasnactionBuilder = Trasnaction.builder();

         if (!request.hasValidCurrency() || !request.hasValidAmount() || !request.hasValidPaymentInstrument()) {
            return trasnactionBuilder
                    .transactionStatus(TransactionStatus.REJECTED)
                    .merchantId(request.getMerchantId())
                    .transactionId(UUID.randomUUID().toString())
                    .errorDetails(ErrorDetails.from(ErrorCode.MISSING_MANDATORY_INFORMATION)).build();

        } else if (!isOnboarded(request.getMerchantId())) {
             return trasnactionBuilder
                     .transactionStatus(TransactionStatus.REJECTED)
                     .transactionId(UUID.randomUUID().toString())
                     .errorDetails(ErrorDetails.from(ErrorCode.INVALID_MERCHANT_ID)).build();
        } else {
             RequestRouter router = new RequestRouter();
             SendPaymentResponse response = router.with(request).resolvePaymentMethod().send();
            return trasnactionBuilder
                    .transactionStatus(response.getTransactionStatus())
                    .merchantId(request.getMerchantId())
                    .transactionId(response.getTransactionId())
                    .instrumentType(request.getPaymentInstrument().getType()).build();
        }
    }

    @GetMapping("/payment/{paymentMethod}/{transactionId}")
    public Trasnaction getTransaction(@PathVariable String paymentMethod, @PathVariable String transactionId) {

        Trasnaction.TrasnactionBuilder trasnactionBuilder = Trasnaction.builder();

        RequestRouter router = new RequestRouter();
        SendPaymentResponse response = router.with(transactionId).resolvePaymentMethod(paymentMethod).then().get();
        return trasnactionBuilder
                .transactionStatus(response.getTransactionStatus())
                .transactionId(response.getTransactionId())
                .instrumentType(PaymentInstrumentType.valueOf(paymentMethod)).build();
    }

    @PutMapping("/payment/{paymentMethod}/{transactionId}/cancel")
    public Trasnaction cancelTrasnaction(@PathVariable String paymentMethod, @PathVariable String transactionId) {

        Trasnaction.TrasnactionBuilder trasnactionBuilder = Trasnaction.builder();

        RequestRouter router = new RequestRouter();
        SendPaymentResponse response = router.with(transactionId).resolvePaymentMethod(paymentMethod).then().cancel();
        return trasnactionBuilder
                .transactionStatus(response.getTransactionStatus())
                .transactionId(response.getTransactionId())
                .instrumentType(PaymentInstrumentType.valueOf(paymentMethod)).build();
    }

    private Boolean isOnboarded(String merchantId) {
        return merchantRepository.existsById(merchantId);
    }
}
