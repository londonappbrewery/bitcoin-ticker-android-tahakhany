package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

class BTCExchangePrice {

    private String currency;
    private String exchangeRate;
    private String tempExchangeRate;

    public BTCExchangePrice(String currency, String exchangeRate) {
        setCurrency(currency);
        setExchangeRate(exchangeRate);
    }

    public BTCExchangePrice(JSONObject jsonObject) {
        try {
            setCurrency(jsonObject.getJSONObject("data").getString("currency"));
            setExchangeRate(jsonObject.getJSONObject("data").getString("amount"));
            System.out.println("Bitcoin Tracker TAG: " + this.currency);
            System.out.println("Bitcoin Tracker TAG: " + this.exchangeRate);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Bitcoin Tracker TAG: " + e.toString());
        }
    }

    @org.jetbrains.annotations.NotNull
    public static BTCExchangePrice fromJSON(JSONObject response) {
        BTCExchangePrice btcExchangePrice = new BTCExchangePrice(response);
        return btcExchangePrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        tempExchangeRate = exchangeRate;
        this.exchangeRate = tempExchangeRate;
    }

    public String getExchangeRate(int accuracy) {
        String output;
        double tempExchangeRate = 0.0;
        tempExchangeRate = Double.parseDouble(getExchangeRate());
        switch (accuracy) {
            case 1:
                output = String.format("%.1f", tempExchangeRate);
                break;
            case 2:
                output = String.format("%.2f", tempExchangeRate);
                break;
            case 3:
                output = String.format("%.3f", tempExchangeRate);
                break;
            default:
                output = getExchangeRate();
                break;
        }
        return output;
    }

    @Override
    public String toString() {
        return "BTCExchangePrice{" +
                "currency='" + currency + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }
}
