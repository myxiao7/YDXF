package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  地图覆盖物信息bean
 * Created by My灬xiao7
 * date: 2016/1/6
 *
 * @version 1.0
 */
public class MapInfo implements Serializable{

    private static final long serialVersionUID = -6090875988594731275L;
    private double latitude;//纬度
    private double langtitude;//经度
    private int img;//图片
    private String name;//名称
    private String distance;//距离

    public MapInfo() {
    }

    public MapInfo(double latitude, double langtitude, int img, String name, String distance) {
        this.latitude = latitude;
        this.langtitude = langtitude;
        this.img = img;
        this.name = name;
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLangtitude() {
        return langtitude;
    }

    public void setLangtitude(double langtitude) {
        this.langtitude = langtitude;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
