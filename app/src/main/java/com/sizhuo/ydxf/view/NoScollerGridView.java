package com.sizhuo.ydxf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 项目名称: YDXF
 * 类描述:  解决GridView 在Listview下只显示一行的问题
 * Created by My灬xiao7
 * date: 2016/1/5
 *
 * @version 1.0
 */
public class NoScollerGridView extends GridView {
    public NoScollerGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public NoScollerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoScollerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
