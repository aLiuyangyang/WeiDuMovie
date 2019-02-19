package com.bw.movie.bean;

public class CinemaPopupDetailsBean {

    /**
     * address : 东城区滨河路乙1号雍和航星园74-76号楼
     * businessHoursContent : 星期一 至 星期日   早07:00:00 - 晚06:30:00
     * commentTotal : 0
     * distance : 0
     * followCinema : 2
     * id : 1
     * logo : http://172.17.8.100/images/movie/logo/qcgx.jpg
     * name : 青春光线电影院
     * phone : 010-64142287
     * vehicleRoute : 青春光线电影院附近的公交站:
     雍和宫桥东、北小街豁口(二环辅)、北小街豁口、雍和宫桥北、雍和宫桥东(西行)、雍和宫站、地坛东门、雍和宫桥东、雍和宫桥东(东行)、地坛东门、和平里南口、雍和宫桥北、和平里南街、雍和宫站、东直门北小街北口、雍和宫。

     青春光线电影院附近的公交车:
     44路、75路、特12路、特2路、13路、18路、116路、130路、684路、117路、机场巴士2线、909路、125路、674路、特16路、地铁2号线、地铁5号线、612路等。

     打车去青春光线电影院多少钱：
     北京市出租车的起步价是13.0元、起步距离3.0公里、 每公里2.3元、燃油附加费1.0元（不超过3.0公里不收） ，请参考。
     */

    private String address;
    private int id;
    private String phone;
    private String vehicleRoute;

    public CinemaPopupDetailsBean(String address, int id, String phone, String vehicleRoute) {
        this.address = address;
        this.id = id;
        this.phone = phone;
        this.vehicleRoute = vehicleRoute;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getVehicleRoute() {
        return vehicleRoute;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setVehicleRoute(String vehicleRoute) {
        this.vehicleRoute = vehicleRoute;
    }
}
