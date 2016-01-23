package com.sizhuo.ydxf.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
@Table(name = "_ReplyData")
public class _ReplyData implements Serializable{
    private static final long serialVersionUID = 3353025876023579373L;
    @Column(name = "id", isId = true)
    private int id;
    /**
     * 所属模块
     */
    @Column(name = "moduleType")
    private String moduleType;

    @Column(name = "parentId" /*, property = "UNIQUE"//如果是一对一加上唯一约束*/)
    private String  parentId; // 外键表id
    /**
     * 头像
     */
    @Column(name = "portrait")
    private String portrait;
    /**
     * 内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 昵称
     */
    @Column(name = "nickName")
    private String nickName;
    /**
     * 回复时间
     */
    @Column(name = "pTime")
    private String pTime;

    public _ReplyData() {
    }

    public _PostDetailData getParent(DbManager db) throws DbException {
        return db.findById(_PostDetailData.class, parentId);
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }
}
