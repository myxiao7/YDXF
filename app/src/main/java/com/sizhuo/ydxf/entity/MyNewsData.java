package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  我的消息bean
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyNewsData {
    private String replyIcon;//回复头像
    private String replyName;//回复ID
    private String replyDate;//回复日期
    private String replyContent;//回复内容
    private String post;//原帖

    public MyNewsData() {
    }

    public MyNewsData(String replyIcon, String replyName, String replyDate, String replyContent, String post) {
        this.replyIcon = replyIcon;
        this.replyName = replyName;
        this.replyDate = replyDate;
        this.replyContent = replyContent;
        this.post = post;
    }

    public String getReplyIcon() {
        return replyIcon;
    }

    public void setReplyIcon(String replyIcon) {
        this.replyIcon = replyIcon;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
