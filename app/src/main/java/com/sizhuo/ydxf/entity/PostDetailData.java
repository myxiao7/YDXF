package com.sizhuo.ydxf.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  单条帖子bean
 * Created by My灬xiao7
 * date: 2016/1/7
 *
 * @version 1.0
 */
public class PostDetailData implements Serializable{
    private static final long serialVersionUID = 8274910201916149106L;
    /**
     * 头像
     */
    private String iconUrl;
    /**
     * id
     */
    private String id;
    /**
     * 日期
     */
    private String date;
    /**
     * 赞
     */
    private String likeCount;
    /**
     * 回复数
     */
    private String replyCount;
    /**
     * 标题
     */
    private String title;
    /**
     * 简略内容
     */
    private String des;
    /**
     * 类型
     */
    private String type;
    /**
     * 图片URL
     */
    private String imgUrl;
    /**
     * 多图
     */
    private List<com.sizhuo.ydxf.entity.imgextra> imgextra;
    /**
     * 回复
     */
    private List<ReplyData> replyDatas;

    public PostDetailData() {
    }

    public PostDetailData(String iconUrl, String id, String date, String likeCount, String replyCount, String title, String des, String type, String imgUrl, List<com.sizhuo.ydxf.entity.imgextra> imgextra, List<ReplyData> replyDatas) {
        this.iconUrl = iconUrl;
        this.id = id;
        this.date = date;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.title = title;
        this.des = des;
        this.type = type;
        this.imgUrl = imgUrl;
        this.imgextra = imgextra;
        this.replyDatas = replyDatas;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<com.sizhuo.ydxf.entity.imgextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<com.sizhuo.ydxf.entity.imgextra> imgextra) {
        this.imgextra = imgextra;
    }

    public List<ReplyData> getReplyDatas() {
        return replyDatas;
    }

    public void setReplyDatas(List<ReplyData> replyDatas) {
        this.replyDatas = replyDatas;
    }
}
