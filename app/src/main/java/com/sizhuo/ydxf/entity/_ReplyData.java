package com.sizhuo.ydxf.entity;

import java.io.Serializable;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
public class _ReplyData implements Serializable{
    private static final long serialVersionUID = 3353025876023579373L;
    /**
     * 头像
     */
    private String portrait;
    /**
     * 内容
     */
    private String content;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 回复时间
     */
    private String pTime;

    public _ReplyData() {
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
