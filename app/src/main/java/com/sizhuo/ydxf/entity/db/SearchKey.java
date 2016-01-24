package com.sizhuo.ydxf.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by win7 on 2016/1/24.
 */
@Table(name="search")
public class SearchKey implements Serializable{
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "str")
    private String str;

    public SearchKey() {
    }

    public SearchKey(String str) {
        this.str = str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
