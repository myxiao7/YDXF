package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  我的评论bean
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyCommentData {
    private String content;//评论内容
    private String date;//日期
    private String title;//原文标题

    public MyCommentData() {
    }

    public MyCommentData(String content, String date, String title) {
        this.content = content;
        this.date = date;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
