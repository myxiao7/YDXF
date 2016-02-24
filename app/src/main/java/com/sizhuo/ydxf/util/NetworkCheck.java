package com.sizhuo.ydxf.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * 项目名称: YDXF
 * 类描述:  网络监测模块
 * Created by My灬xiao7
 * date: 2016/2/24
 *
 * @version 1.0
 */
public class NetworkCheck {

    private Context context;
    public enum  NetState {NET_WIFI, NET_3G, NET_NULL};

    public NetworkCheck() {
        super();
    }
    public NetworkCheck(Context context) {
        this.context = context;
    }

    /**
     * 网络检查
     * @return true  已连接
     * @return false 无网络
     */
    public NetState state(){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
//        State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        boolean wifiFlag = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean mobileFlag = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifiFlag){
            return NetState.NET_WIFI;
        }else if(mobileFlag){
            return NetState.NET_3G;
        }else{
            return NetState.NET_NULL;
        }
    }
}