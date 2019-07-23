package com.moneyapi.mongo;

import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Balance {
    @Id
    private String id;
    private Decimal128 balance;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date lastUpdate;

    public Balance() {
    }

    public Balance(String balance, Date lastUpdate) {
        this.balance = Decimal128.parse(balance);
        this.lastUpdate = lastUpdate;
    }

    public String getBalance() {
        return balance.toString();
    }

    public String getlastUpdate() {
        return lastUpdate.toString();
    }

    public void setbalance(String balance) {
        this.balance = Decimal128.parse(balance);
    }

    public void setlastUpdate(String lastUpdate) throws ParseException {
        DateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
        this.lastUpdate = df.parse(lastUpdate);
    }

    public String updateBalance(String amount) {
        this.balance = new Decimal128(this.balance.bigDecimalValue().add(Decimal128.parse(amount).bigDecimalValue()));
        return balance.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "Balance[id=%s, balance='%s', lastUpdate='%s']",
                id, balance, lastUpdate);
    }
}
