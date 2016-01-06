package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  三图
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
public class imgextra implements Serializable{
    private static final long serialVersionUID = -12469631560618241L;
    /**
     * 图片地址
     */
    private String url;

    public imgextra() {
    }

    public imgextra(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
