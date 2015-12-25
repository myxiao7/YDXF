package com.sizhuo.ydxf.entity;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:新闻数据
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
public class NewsData {
    /**
     * 标题
     */
    private String title;
     /**
     * 缩略图
     */
    private String imgsrc;
/**
     * 类型
     */
    private String type;
/**
     * 简略信息
     */
    private String digest;
/**
     * 新闻ID
     */
    private String docid;
/**
     * 新闻链接
     */
    private String url;
/**
     * 新闻时间
     */
    private String ptime;
/**
     * 多图新闻
     */
    private List<com.sizhuo.ydxf.entity.imgextra> imgextra;

    public NewsData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<com.sizhuo.ydxf.entity.imgextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<com.sizhuo.ydxf.entity.imgextra> imgextra) {
        this.imgextra = imgextra;
    }
}