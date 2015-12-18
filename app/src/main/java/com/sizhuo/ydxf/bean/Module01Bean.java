package com.sizhuo.ydxf.bean;

/**
 * 项目名称: YDXF
 * 类描述:  栏目一bean
 * Created by My灬xiao7
 * date: 2015/12/18
 *
 * @version 1.0
 */
public class Module01Bean {
    /**
     * 缩略图
     */
    private int Img;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String des;
    /**
     * 时间
     */
    private String date;

    public Module01Bean(int img, String title, String des, String date) {
        Img = img;
        this.title = title;
        this.des = des;
        this.date = date;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
