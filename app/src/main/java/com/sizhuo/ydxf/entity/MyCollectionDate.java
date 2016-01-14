package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  收藏bean
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyCollectionDate {
    private String title;//标题
    private String date;//标题
    private String replyCount;//标题
    //还需要一个单条新闻的数据  包括url，评论数，评论详情等


    public MyCollectionDate() {
    }

    public MyCollectionDate(String title, String date, String replyCount) {
        this.title = title;
        this.date = date;
        this.replyCount = replyCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }
}
