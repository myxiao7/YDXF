package com.sizhuo.ydxf.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  帖子数据bean
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
@Table(name = "_PostDetailData")
public class _PostDetailData implements Serializable{
    private static final long serialVersionUID = 577054091361505244L;
    @Column(name = "id", isId = true)
    private int cid;
    /**
     * 所属模块
     */
    @Column(name = "moduleType")
    private String moduleType;
    /**
     * id
     */
    @Column(name = "forumId")
    private String id;
    /**
     * 头像
     */
    @Column(name = "portrait")
    private String portrait;
    /**
     * 昵称
     */
    @Column(name = "nickName")
    private String nickName;
    /**
     * 标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 图片
     */
    @Column(name = "imgsrc")
    private String imgsrc;
    /**
     * 类型
     */
    @Column(name = "type")
    private String type;
    /**
     * 简略内容
     */
    @Column(name = "digest")
    private String digest;
    /**
     * 日期
     */
    @Column(name = "ptime")
    private String ptime;
    /**
     * 回复数
     */
    @Column(name = "replyCount")
    private String replyCount;
    /**
     * 多图
     */
    private List<com.sizhuo.ydxf.entity.imgextra> imgextra;
    /**
     * 回复
     */
    private List<_ReplyData> reply;

    public List<imgextra> getChildren(DbManager db) throws DbException {
        return db.selector(imgextra.class).where("parentId", "=", this.id).findAll();
    }

    public List<_ReplyData> getChildren2(DbManager db) throws DbException {
        return db.selector(_ReplyData.class).where("parentId", "=", this.id).findAll();
    }

    public _PostDetailData() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

}
