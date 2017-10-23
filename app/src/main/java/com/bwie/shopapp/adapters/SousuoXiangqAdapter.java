package com.bwie.shopapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.SouSuoBean;

import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/11
 */

public class SousuoXiangqAdapter extends BaseAdapter {
    private Context context;
    private List<SouSuoBean.DatasBean.GoodsListBean> list;

    public SousuoXiangqAdapter(Context context, List<SouSuoBean.DatasBean.GoodsListBean> list) {
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
        MyViewHolder myViewHolder =null;
        if (view==null){
            view=View.inflate(context, R.layout.sousxiangqlistviewitem,null);
            myViewHolder = new MyViewHolder();
            myViewHolder.iv=view.findViewById(R.id.iv);
            myViewHolder.tv_name=view.findViewById(R.id.tv_name);
            myViewHolder.tv_price=view.findViewById(R.id.tv_price);
            myViewHolder.tv_story=view.findViewById(R.id.tv_story);
            view.setTag(myViewHolder);
        }else{
            myViewHolder= (MyViewHolder) view.getTag();
        }
        myViewHolder.tv_name.setText(list.get(i).getGoods_name());
        myViewHolder.tv_price.setText("￥"+list.get(i).getGoods_price());
        myViewHolder.tv_story.setText(list.get(i).getStore_name());
        Glide.with(context).load(list.get(i).getGoods_image_url()).into(myViewHolder.iv);

        return view;
    }

    class MyViewHolder {
        ImageView iv;
        TextView tv_name;
        TextView tv_price;
        TextView tv_story;
    }
}
