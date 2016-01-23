package com.sizhuo.ydxf.entity;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  三图
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
@Table(name = "imgextra")
public class imgextra implements Serializable{
    private static final long serialVersionUID = -12469631560618241L;
    @Column(name = "id", isId = true)
    private int id;

    /**
     * 所属模块
     */
    @Column(name = "moduleType")
    private String moduleType;

    /**
     * 图片地址
     */
    @Column(name = "url")
    private String url;


    @Column(name = "parentId" /*, property = "UNIQUE"//如果是一对一加上唯一约束*/)
    private String  parentId; // 外键表id

    public _PostDetailData getParent(DbManager db) throws DbException {
        return db.findById(_PostDetailData.class, parentId);
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String  getParentId() {
        return parentId;
    }

    public void setParentId(String  parentId) {
        this.parentId = parentId;
    }

    public imgextra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public imgextra(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
