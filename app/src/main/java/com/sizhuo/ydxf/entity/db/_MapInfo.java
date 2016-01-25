package com.sizhuo.ydxf.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  地图覆盖物信息bean
 * Created by My灬xiao7
 * date: 2016/1/6
 *
 * @version 1.0
 */
@Table(name="_MapInfo")
public class _MapInfo implements Serializable{

    private static final long serialVersionUID = -6090875988594731275L;
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "latitude")
    private double latitude;//纬度
    @Column(name = "longitude")
    private double longitude;//经度
    @Column(name = "picture")
    private String picture;//图片
    @Column(name = "name")
    private String name;//名称
    @Column(name = "synopsis")
    private String synopsis;//简介
    @Column(name = "position")
    private String position;//位置


    public _MapInfo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }



    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
