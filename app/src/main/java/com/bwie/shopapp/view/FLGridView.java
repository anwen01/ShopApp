package com.bwie.shopapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * 作者：张玉轲
 * 时间：2017/10/10
 */

public class FLGridView extends GridView {
    public FLGridView(Context context) {
        super(context);
    }

    public FLGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FLGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
