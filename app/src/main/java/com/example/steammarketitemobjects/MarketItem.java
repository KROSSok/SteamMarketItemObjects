package com.example.steammarketitemobjects;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class MarketItem implements Serializable {

    private Boolean success;
    private Double lowest_price;
    private int volume;
    private Double median_price;
    private String time;
    private String currency;

    public Boolean getSuccess() {
        return success;
    }

    public Double getLowest_price() {
        return lowest_price;
    }

    public int getVolume() {
        return volume;
    }

    public Double getMedian_price() {
        return median_price;
    }

    public String getTime(){
        return time;
    }

    public String getCurrency(){
        return currency;
    }

    public MarketItem(Boolean success, Double lowest_price, int volume, Double median_price, String currency) {
        this.success = success;
        this.lowest_price = lowest_price;
        this.median_price = median_price;
        this.volume = volume;
        this.time = (new SimpleDateFormat("yyyy/MM/dd '-' HH:mm:ss.sss z")).format(System.currentTimeMillis());
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "MarketItem " +
                "success=" + success +
                ", lowest_price='" + lowest_price +
                " " + currency + '\'' +
                ", volume='" + volume + '\'' +
                ", median_price='" + median_price +
                " " + currency + '\'' +
                ", time='" + time + '\'' +
                ' ';
    }
}
