package com.example.paymentgateway.data;

import com.example.paymentgateway.domain.MerchantResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MerchantRepository extends MongoRepository<MerchantResponse, String> {
}
