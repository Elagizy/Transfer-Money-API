package com.moneyapi.mongo;

import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Transactions {
    @Id
    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date time;
    private Decimal128 amount;
    private String currentBalance;

    public Transactions() {
    }

    public Transactions(Decimal128 amount, Date time, String currentBalance) {
        this.amount = amount;
        this.time = time;
        this.currentBalance = currentBalance;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount.toString();
    }

    public String getTime() {
        return time.toString();
    }

    public String getcurrentBalance() {
        return currentBalance;
    }

    @Override
    public String toString() {
        return String.format(
                "Balance[id=%s, time='%s', amount='%s', currentBalance='%s']",
                id, time, amount, currentBalance);
    }
}
