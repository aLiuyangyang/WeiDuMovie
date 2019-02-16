package com.bw.movie.bean;

public class PayTranDataBean {

    private String orderId;
    private int totalPrice;

    public PayTranDataBean(String orderId, int totalPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
