package com.mcx.model;

/**
 * Created by prasanth.p on 30/04/18.
 */
public class Notification {
    private String type;
    private String name;
    private String period;
    private String stopLoss;
    private String BET;
    private String TGT;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(String stopLoss) {
        this.stopLoss = stopLoss;
    }

    public String getBET() {
        return BET;
    }

    public void setBET(String BET) {
        this.BET = BET;
    }

    public String getTGT() {
        return TGT;
    }

    public void setTGT(String TGT) {
        this.TGT = TGT;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", period='" + period + '\'' +
                ", stopLoss='" + stopLoss + '\'' +
                ", BET='" + BET + '\'' +
                ", TGT='" + TGT + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
