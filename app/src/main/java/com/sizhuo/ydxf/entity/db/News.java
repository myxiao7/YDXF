package com.sizhuo.ydxf.entity.db;

import com.sizhuo.ydxf.entity._ReplyData;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/20
 *
 * @version 1.0
 */
@Table(name="news")
public class News {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "title")
    private String title;
    @Column(name = "username")
    private String username;
    @Column(name = "docid",property = "UNIQUE")
    private String docid;
    @Column(name = "url")
    private String url;
    @Column(name = "ptime")
    private String ptime;
    @Column(name = "replyCount")
    private String replyCount;
    @Column(name = "reply")
    private List<_ReplyData> reply;

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public List<_ReplyData> getReply() {
        return reply;
    }

    public void setReply(List<_ReplyData> reply) {
        this.reply = reply;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
