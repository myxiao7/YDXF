package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/2/23
 *
 * @version 1.0
 */
public class VideoList implements Serializable {

    private static final long serialVersionUID = 5501420227892781557L;
    /**
     * 标题
     */
    private String title;
     /**
     * synopsis
     */
    private String synopsis;
     /**
     * 缩略图
     */
    private String picture;
     /**
     * 标清
     */
    private String sdAddress;
     /**
     * 普清
     */
    private String hdAddress;
    /**
     * 时间
     */
    private String pTime;

    public VideoList() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSdAddress() {
        return sdAddress;
    }

    public void setSdAddress(String sdAddress) {
        this.sdAddress = sdAddress;
    }

    public String getHdAddress() {
        return hdAddress;
    }

    public void setHdAddress(String hdAddress) {
        this.hdAddress = hdAddress;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }
}
