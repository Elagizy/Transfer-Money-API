package com.moneyapi.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BalanceRepo extends MongoRepository<Balance, String> {
    Balance findTopByOrderByLastUpdateAsc();
}