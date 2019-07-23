package com.moneyapi.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionsRepo extends MongoRepository<Transactions, String> {

}