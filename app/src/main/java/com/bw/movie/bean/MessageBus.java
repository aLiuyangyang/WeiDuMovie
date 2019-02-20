package com.bw.movie.bean;

/**
 * date:2019/2/20
 * author:刘洋洋(DELL)
 * function:
 */
public class MessageBus {
    private double mylatitude;
    private double mylongitude;

    public MessageBus(double mylatitude, double mylongitude) {
        this.mylatitude = mylatitude;
        this.mylongitude = mylongitude;
    }


    public double getMylatitude() {
        return mylatitude;
    }

    public void setMylatitude(double mylatitude) {
        this.mylatitude = mylatitude;
    }

    public double getMylongitude() {
        return mylongitude;
    }

    public void setMylongitude(double mylongitude) {
        this.mylongitude = mylongitude;
    }
}
