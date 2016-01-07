package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  回复bean
 * Created by My灬xiao7
 * date: 2016/1/7
 *
 * @version 1.0
 */
public class ReplyData implements Serializable{
    private static final long serialVersionUID = -7519305554877894522L;
    private String icon;//头像
    private String name;//用户
    private String content;//回复内容
    private String date;//回复时间

    public ReplyData() {
    }

    public ReplyData(String icon, String name, String content, String date) {
        this.icon = icon;
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
