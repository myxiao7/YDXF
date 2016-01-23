package com.sizhuo.ydxf.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:新闻数据
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
@Table(name = "_NewsData")
public class _NewsData implements Serializable {
    private static final long serialVersionUID = -228286441826334138L;
    @Column(name = "id", isId = true)
    private int id;

    /**
     * 所属模块
     */
    @Column(name = "moduleType")
    private String moduleType;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 缩略图
     */
    @Column(name = "imgsrc")
    private String imgsrc;
    /**
     * 类型
     */
    @Column(name = "type")
    private String type;
    /**
     * 简略信息
     */
    @Column(name = "digest")
    private String digest;
    /**
     * 新闻ID
     */
    @Column(name = "docid")
    private String docid;
    /**
     * 新闻链接
     */
    @Column(name = "url")
    private String url;
    /**
     * 新闻时间
     */
    @Column(name = "ptime")
    private String ptime;
    /**
     * 多图新闻
     */
    @Column(name = "imgextra")
    private List<com.sizhuo.ydxf.entity.imgextra> imgextra;
    /**
     * 评价数
     */
    @Column(name = "replyCount")
    private String replyCount;
    /**
     * 评价
     */
    @Column(name = "reply")
    private List<_ReplyData> reply;

    public _NewsData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
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

    public List<com.sizhuo.ydxf.entity.imgextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<com.sizhuo.ydxf.entity.imgextra> imgextra) {
        this.imgextra = imgextra;
    }

    public List<_ReplyData> getReply() {
        return reply;
    }

    public void setReply(List<_ReplyData> reply) {
        this.reply = reply;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }
}
