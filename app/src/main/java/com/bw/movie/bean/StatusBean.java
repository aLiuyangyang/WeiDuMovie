package com.bw.movie.bean;

/**
 * date:2019/2/18
 * author:刘洋洋(DELL)
 * function:
 */
public class StatusBean {

    /**
     * message : 状态改变成功
     * status : 0000
     */

    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}