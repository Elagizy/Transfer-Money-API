package com.moneyapi;

import com.moneyapi.mongo.Balance;
import com.moneyapi.mongo.BalanceRepo;
import com.moneyapi.mongo.Transactions;
import com.moneyapi.mongo.TransactionsRepo;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Date;

@RestController
public class CreateController {
    @Autowired
    private TransactionsRepo transactions;
    @Autowired
    private BalanceRepo balance;

    @RequestMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Transactions create(@RequestParam(value = "amount") String amount) throws ParseException {
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
        RestTemplate restTemplate = new RestTemplate();
        Balance verifyBalance = restTemplate.getForObject("http://localhost:8080/retrieve", Balance.class);
        if (verifyBalance != null && verifyBalance.getBalance().equals(b.getBalance())) {
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
