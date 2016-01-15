package com.sizhuo.ydxf.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 项目名称: YDXF
 * 类描述:  用户表
 * Created by My灬xiao7
 * date: 2016/1/15
 *
 * @version 1.0
 */
@Table(name = "user")
public class User {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "userName")
    private String userName;
    @Column(name = "userPwd")
    private String userPwd;
    @Column(name = "nickName")
    private String nickName;
    @Column(name = "sex")
    private String sex;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "portrait")
    private String portrait;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

}
