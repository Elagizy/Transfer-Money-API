package com.moneyapi;

import com.moneyapi.mongo.Balance;
import com.moneyapi.mongo.BalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RetrieveController {
    @Autowired
    private BalanceRepo balance;

    @RequestMapping(value = "/retrieve", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Balance retrieve() {
        Balance b;
        if (balance.findAll().size() == 0) {
            b = new Balance("0", new Date());
            balance.save(b);
        } else {
            b = balance.findTopByOrderByLastUpdateAsc();
        }

        return b;
    }
}