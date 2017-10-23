package com.bwie.shopapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.HomePageBean;

import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/9
 */

public class HomePage2Adapter extends BaseAdapter {
    private Context context;
    private List<HomePageBean.DataBean.Ad5Bean> list;

    public HomePage2Adapter(Context context, List<HomePageBean.DataBean.Ad5Bean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view=View.inflate(context, R.layout.home2item,null);
        }
        ImageView iv=view.findViewById(R.id.iv_homepage2);
        TextView tv=view.findViewById(R.id.tv_homepage2);
        tv.setText(list.get(i).getTitle());
        Glide.with(context).load(list.get(i).getImage()).into(iv);

        return view;
    }
}
