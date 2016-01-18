package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  便民114Bean
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
public class _AddListData implements Serializable{

    private static final long serialVersionUID = 5478213526223591446L;
    /**
     * ID
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 图标
     */
    private String picture;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 网址
     */
    private String website;
    /**
     * 地址
     */
    private String add;

    public _AddListData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
