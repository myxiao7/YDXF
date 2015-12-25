package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  轮播图Bean
 * Created by My灬xiao7
 * date: 2015/12/22
 *
 * @version 1.0
 */
public class SliderBean {
    /**
     * 标题
     */
    private String title;
    /**
     * 图片地址
     */
    private String url;

    public SliderBean(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
