package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/28
 *
 * @version 1.0
 */

/**
 * 视频源信息
 */
public class VideoInfo {
    public enum VideoType{
        HLS,
        MP4,
        MP3,
        AAC,
        FMP4,
        WEBM,
        MKV,
        TS,
    }
    //视频地址
    public String url;
    //视频类型
    public VideoType type;
    //视频清晰度，如：高清等
    public String description;
}