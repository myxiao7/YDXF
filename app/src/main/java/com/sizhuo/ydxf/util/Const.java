package com.sizhuo.ydxf.util;

/**
 * 项目名称: YDXF
 * 类描述:  相关请求的URL
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
public class Const {
    /**
     * URL
     */
    public static final String URL = "http://192.168.1.114:8080/xinwen/";
    public static final String URL2 = "http://112.54.80.235:50406/IndustryPioneer.svc/";
    /**
     * 主界面
     */
    public static final String MAIN_URL =URL2 + "selectIndex";
    /**
     * 组织机构
     */
    public static final String ORGANIZATION =URL2 + "selectConvenience";
    /**
     * 便民114
     */
    public static final String ADDRESSLIST =URL2 + "selectDirectory";
    /**
     * Module01
     */
    public static final String M01 = URL2 + "selectNews/1/";
    /**
     * Module02
     */
    public static final String M02 = URL2 + "selectNews/2/";
    /**
     * Module03
     */
    public static final String M03 = URL2 + "selectNews/3/";
    /**
     * Module04
     */
    public static final String M04 = URL2 + "selectNews/4/";
    /**
     * Module05
     */
    public static final String M05 = URL2 + "selectNews/5/";
    /**
     * 帖子评价
     */
    public static final String POSTREPLY = URL2+"insertReply";
    /**
     * 论坛
     */
    public static final String MFORUM = URL2+"selectCard/1/";
    /**
     * 发帖
     */
    public static final String PUBLISH = URL2+"insertCard";
    /**
     * 获取新闻评价
     */
    public static final String SELNEWSCOMMENT = URL2+"selectNewsComment/";
    /**
     * 评价新闻
     */
    public static final String NEWSCOMMENT = URL2+"insertComment";
    /**
     * 收藏新闻
     */
    public static final String NEWSLOVE = URL2+"insertCollection";
    /**
     * 我的收藏
     */
    public static final String MYCOLLECTION = URL2+"selectNewsCollection/";
    /**
     * 我的评论
     */
    public static final String MYCOMMENT = URL2+"selectMyNewsComment/";
    /**
     * 我的帖子
     */
    public static final String MYPOST = URL2+"selectMyCard/";
    /**
     * 我的消息
     */
    public static final String MYNEWS = URL2+"selectNewsCollection/";

    /**
     * 登录
     */
    public static final String LOGIN = URL2 + "login";
    /**
     * 注册
     */
    public static final String REGISTER = URL2 + "insertUser";
    /**
     * 修改信息
     */
    public static final String UPDATEINFO = URL2 + "updateUser";

}
