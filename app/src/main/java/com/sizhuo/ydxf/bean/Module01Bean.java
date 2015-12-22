package com.sizhuo.ydxf.bean;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  栏目一bean
 * Created by My灬xiao7
 * date: 2015/12/18
 *
 * @version 1.0
 */
public class Module01Bean {
    /**
     * 轮播图
     */
    private SliderBean sliderBean;
    /**
     * 新闻list
     */
    private List<NewsBean> newsBeanList;
    /**
     * 横向新闻list
     */
    private List<NewsHonBean> newsHonBeanList;

    /**
     *
     * @param sliderBean 轮播图
     * @param newsBeanList 新闻list
     * @param newsHonBeanList 横向新闻list
     */
    public Module01Bean(List<NewsBean> newsBeanList, SliderBean sliderBean, List<NewsHonBean> newsHonBeanList) {
        this.newsBeanList = newsBeanList;
        this.sliderBean = sliderBean;
        this.newsHonBeanList = newsHonBeanList;
    }

    public SliderBean getSliderBean() {
        return sliderBean;
    }

    public void setSliderBean(SliderBean sliderBean) {
        this.sliderBean = sliderBean;
    }

    public List<NewsBean> getNewsBeanList() {
        return newsBeanList;
    }

    public void setNewsBeanList(List<NewsBean> newsBeanList) {
        this.newsBeanList = newsBeanList;
    }

    public List<NewsHonBean> getNewsHonBeanList() {
        return newsHonBeanList;
    }

    public void setNewsHonBeanList(List<NewsHonBean> newsHonBeanList) {
        this.newsHonBeanList = newsHonBeanList;
    }
}
