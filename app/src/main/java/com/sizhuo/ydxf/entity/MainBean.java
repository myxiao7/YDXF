package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  主菜单Bean
 * Created by My灬xiao7
 * date: 2015/12/17
 *
 * @version 1.0
 */
public class MainBean {
    /**
     * 缩略图图片地址
     */
    private String imgUrl;
    /**
     * 新闻标题
     */
    private String title;
    /**
     * 新闻简介
     */
    private String des;
    /**
     * 新闻详细地址
     */
    private String detailUrl;

    public MainBean(String imgUrl, String title, String des, String detailUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.des = des;
        this.detailUrl = detailUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
