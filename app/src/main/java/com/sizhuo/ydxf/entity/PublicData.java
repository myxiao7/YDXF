package com.sizhuo.ydxf.entity;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/4
 *
 * @version 1.0
 */
public class PublicData {
    private String userName;
    private String userPwd;
    private String IP;
    private String title;
    private String content;
    private List<imgextra> imgextra;

    public PublicData(String userName, String userPwd, String IP, String title, String content, List<imgextra> imgextra) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.IP = IP;
        this.title = title;
        this.content = content;
        this.imgextra = imgextra;
    }

    public PublicData(String userName, String userPwd, String IP, String title, String content) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.IP = IP;
        this.title = title;
        this.content = content;
    }

    public PublicData() {
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

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<imgextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<imgextra> imgextra) {
        this.imgextra = imgextra;
    }
}
