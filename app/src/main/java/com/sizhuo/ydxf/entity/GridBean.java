package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  便民窗口bean
 * Created by My灬xiao7
 * date: 2015/12/18
 *
 * @version 1.0
 */
public class GridBean {
    /**
     * 图标
     */
    private String img;
    /**
     * 标题
     */
    private String title;

    public GridBean(String img, String title) {

        this.img = img;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
