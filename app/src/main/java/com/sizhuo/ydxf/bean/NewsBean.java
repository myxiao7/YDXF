package com.sizhuo.ydxf.bean;

/**
 * 项目名称: YDXF
 * 类描述:  新闻主体bean
 * Created by My灬xiao7
 * date: 2015/12/22
 *
 * @version 1.0
 */
public class NewsBean {
    /**
     * 缩略图01
     */
    private String img01;
    /**
     * 缩略图02
     */
    private String img02;
    /**
     * 缩略图03
     */
    private String img03;
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
    /**
     * 新闻类型
     */
    private String type;
    /**
     * 新闻链接
     */
    private String url;

    public NewsBean(String img01, String img02, String img03, String title, String des, String date, String type, String url) {
        this.img01 = img01;
        this.img02 = img02;
        this.img03 = img03;
        this.title = title;
        this.des = des;
        this.date = date;
        this.type = type;
        this.url = url;
    }

    public String getImg01() {
        return img01;
    }

    public void setImg01(String img01) {
        this.img01 = img01;
    }

    public String getImg02() {
        return img02;
    }

    public void setImg02(String img02) {
        this.img02 = img02;
    }

    public String getImg03() {
        return img03;
    }

    public void setImg03(String img03) {
        this.img03 = img03;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
