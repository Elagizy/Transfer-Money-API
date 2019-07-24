package com.moneyapi;

import com.moneyapi.mongo.Balance;
import com.moneyapi.mongo.BalanceRepo;
import com.moneyapi.mongo.Transactions;
import com.moneyapi.mongo.TransactionsRepo;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
public class CreateController {
    @Autowired
    private TransactionsRepo transactions;
    @Autowired
    private BalanceRepo balance;
    @Autowired
    public RestTemplate restTemplate;

    Balance load() {
        return restTemplate.getForObject("http://localhost:8080/retrieve", Balance.class);
    }

    @RequestMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Transactions create(@RequestPart String amount) {
        // Update Balance
        Balance b;
        if (balance.findAll().size() == 0) {
            b = new Balance(amount, new Date());
        } else {
            b = balance.findTopByOrderByLastUpdateAsc();
            b.updateBalance(amount);
        }
        balance.save(b);
        // Verify balance By calling retrieve API.
        Balance verifyBalance = load();
        if (verifyBalance.getBalance().equals(b.getBalance())) {
            // Save Transaction
            Transactions t;
            t = new Transactions(Decimal128.parse(amount), new Date(), verifyBalance.getBalance());
            transactions.save(t);
            return t;
        } else {
            throw new NullPointerException("Transaction failed!!");
        }
    }

}
