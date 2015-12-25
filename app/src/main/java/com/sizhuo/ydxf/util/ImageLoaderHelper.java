package com.sizhuo.ydxf.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.sizhuo.ydxf.R;

/**
 * 项目名称: YDXF
 * 类描述:  网络图片加载UIL
 * Created by My灬xiao7
 * date: 2015/12/25
 *
 * @version 1.0
 */
public class ImageLoaderHelper {
    private static ImageLoaderHelper imageLoaderHelper;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public static ImageLoaderHelper getIstance(){
        if(imageLoaderHelper == null){
            imageLoaderHelper = new ImageLoaderHelper();
        }
        return imageLoaderHelper;
    }

    private ImageLoaderHelper() {
        init();
    }

    private void init() {
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    /**
     * 加载网络图片
     * @param url 图片地址
     * @param imageView 加载容器
     */
    public void loadImg(String url , ImageView imageView){
        url = url.trim();
        imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, options);
    }

}
