package com.bw.movie.bean;

/**
 * date:2019/2/18
 * author:刘洋洋(DELL)
 * function:
 */
public class SelectUserNoReadBean {

    /**
     * count : 1
     * message : 查询成功
     * status : 0000
     */

    private int count;
    private String message;
    private String status;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
