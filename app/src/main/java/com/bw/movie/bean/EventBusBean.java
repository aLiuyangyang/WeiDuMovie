package com.bw.movie.bean;

/**
 * date:2019/2/15
 * author:刘洋洋(DELL)
 * function:
 */
public class EventBusBean {
    private int id;
    private String flag;

    public EventBusBean(int id, String flag) {
        this.id = id;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
