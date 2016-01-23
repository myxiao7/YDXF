package com.sizhuo.ydxf.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  轮播图
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
@Table(name = "_SliderData")
public class _SliderData implements Serializable{

    private static final long serialVersionUID = -8588804606811451379L;
    @Column(name = "id", isId = true)
    private int id;
    /**
     * 所属模块
     */
    @Column(name = "moduleType")
    private String moduleType;
    /**
     * 轮播标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 轮播简介
     */
    @Column(name = "digest")
    private String digest;
     /**
     * 轮播缩略图
     */
     @Column(name = "imgsrc")
    private String imgsrc;
     /**
     * 轮播新闻ID
     */
     @Column(name = "docid")
    private String docid;
     /**
     * 轮播新闻URL
     */
     @Column(name = "url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
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

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public _SliderData(String title, String imgsrc, String docid, String url) {

        this.title = title;
        this.imgsrc = imgsrc;
        this.docid = docid;
        this.url = url;
    }

    public _SliderData() {
    }
}
