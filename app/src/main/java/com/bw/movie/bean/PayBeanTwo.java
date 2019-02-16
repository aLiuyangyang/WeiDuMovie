package com.bw.movie.bean;

import java.io.Serializable;

/**
 * @author Mr.Right
 * @date 2019/2/14
 */
public class PayBeanTwo implements Serializable {
    private String appId;
    private String message;
    private String nonceStr;
    private String partnerId;
    private String prepayId;
    private String sign;
    private String status;
    private String timeStamp;
    private String packageValue;

    public PayBeanTwo(String appId, String message, String nonceStr, String partnerId, String prepayId, String sign, String status, String timeStamp, String packageValue) {
        this.appId = appId;
        this.message = message;
        this.nonceStr = nonceStr;
        this.partnerId = partnerId;
        this.prepayId = prepayId;
        this.sign = sign;
        this.status = status;
        this.timeStamp = timeStamp;
        this.packageValue = packageValue;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }
}
