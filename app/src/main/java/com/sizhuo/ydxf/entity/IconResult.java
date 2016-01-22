package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/21
 *
 * @version 1.0
 */
public class IconResult implements Serializable{

    private static final long serialVersionUID = -397900636916621103L;
    private String code;
    private String url;

    public IconResult() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
