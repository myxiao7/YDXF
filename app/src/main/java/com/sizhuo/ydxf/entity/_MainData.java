package com.sizhuo.ydxf.entity;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  主业数据bean
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
public class _MainData {
    /**
     * 轮播图数据集合
     */
    private List<_SliderData> carousel;
    /**
     * 新闻数据集合
     */
    private List<_NewsData> news;
    /**
     *论坛数据集合
     */
    private List<_PostDetailData> card;
    /**
     * 组织机构数据集合
     */
    private List<_ServiceData> convenience;
    /**
     * 便民114数据集合
     */
    private List<_ServiceData> directory;

    public _MainData() {
    }

    public List<_SliderData> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<_SliderData> carousel) {
        this.carousel = carousel;
    }

    public List<_NewsData> getNews() {
        return news;
    }

    public void setNews(List<_NewsData> news) {
        this.news = news;
    }

    public List<_PostDetailData> getCard() {
        return card;
    }

    public void setCard(List<_PostDetailData> card) {
        this.card = card;
    }

    public List<_ServiceData> getConvenience() {
        return convenience;
    }

    public void setConvenience(List<_ServiceData> convenience) {
        this.convenience = convenience;
    }

    public List<_ServiceData> getDirectory() {
        return directory;
    }

    public void setDirectory(List<_ServiceData> directory) {
        this.directory = directory;
    }
}
