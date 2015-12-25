package com.sizhuo.ydxf.bean;

/**
 * 项目名称: YDXF
 * 类描述:  轮播图
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
public class SliderData {
    /**
     * 轮播标题
     */
    private String title;
     /**
     * 轮播缩略图
     */
    private String imgsrc;
     /**
     * 轮播新闻ID
     */
    private String docid;
     /**
     * 轮播新闻URL
     */
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SliderData(String title, String imgsrc, String docid, String url) {

        this.title = title;
        this.imgsrc = imgsrc;
        this.docid = docid;
        this.url = url;
    }

    public SliderData() {
    }
}
