package com.sizhuo.ydxf.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  论坛bean
 * Created by My灬xiao7
 * date: 2015/12/30
 *
 * @version 1.0
 */
public class ForumData implements Serializable{
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

    public ForumData() {
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
}
