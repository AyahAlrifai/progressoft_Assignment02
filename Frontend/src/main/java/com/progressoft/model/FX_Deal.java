package com.progressoft.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * FXDeal HISTORY class
 * model in java for table FX_DealHistory in database
 * 
 * @author Ayah Al-refai
 */
public class FX_Deal implements Serializable {
    /**
     * unique id for each fx deal history
     * <p>
     * PK
     * <p>
     * NOT NULL
     */
    private long deal_Id;
    /**
     * From Currency ISO Code "Ordering Currency"
     */
    private String fromISO;
    /**
     * To Currency ISO Code "Ordering Currency"
     */
    private String toISO;
    /**
     * Deal Amount in ordering currency
     */
    private float amount;
    /**
     * Deal timestamp
     * <p>
     * NOT NULL
     */
    private LocalDate timeStamp;

    public FX_Deal() {
        super();
    }

    public long getDeal_Id() {
        return deal_Id;
    }

    public void setDeal_Id(long deal_Id) {
        this.deal_Id = deal_Id;
    }

    public String getFromISO() {
        return fromISO;
    }

    public void setFromISO(String fromISO) {
        this.fromISO = fromISO;
    }

    public String getToISO() {
        return toISO;
    }

    public void setToISO(String toISO) {
        this.toISO = toISO;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "FX_Deal [amount=" + amount + ",\t fromISO=" + fromISO + ",\t toISO=" + toISO  + ",\t timeStamp=" + timeStamp
                + "]";
    }

    
}
