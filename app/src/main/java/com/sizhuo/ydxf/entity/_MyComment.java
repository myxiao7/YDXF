package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  我的评论bean
 * Created by My灬xiao7
 * date: 2016/1/20
 *
 * @version 1.0
 */
public class _MyComment {
    /**
     * 新闻信息
     */
    private _NewsData news;
    /**
     * 评论信息
     */
    private _ReplyData comment;

    public _MyComment() {
    }

    public _NewsData getNews() {
        return news;
    }

    public void setNews(_NewsData news) {
        this.news = news;
    }

    public _ReplyData getComment() {
        return comment;
    }

    public void setComment(_ReplyData comment) {
        this.comment = comment;
    }
}
