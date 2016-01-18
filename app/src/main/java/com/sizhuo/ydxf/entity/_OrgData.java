package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  组织机构bean
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
public class _OrgData implements Serializable{
    private static final long serialVersionUID = -3232404240299118546L;
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
     * 介绍
     */
    private String introduces;

    public _OrgData() {
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

    public String getIntroduces() {
        return introduces;
    }

    public void setIntroduces(String introduces) {
        this.introduces = introduces;
    }
}
