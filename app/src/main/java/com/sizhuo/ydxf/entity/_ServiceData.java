package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  服务bean
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
public class _ServiceData {
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

    public _ServiceData() {
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
}
