package com.bwie.shopapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.HomePageBean;

import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/10
 */

public class HomePage4Adapter extends RecyclerView.Adapter {
    private Context context;
    private List<HomePageBean.DataBean.SubjectsBean.GoodsListBeanX> list;

    public HomePage4Adapter(Context context,List<HomePageBean.DataBean.SubjectsBean.GoodsListBeanX> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home4item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
                Glide.with(context).load(list.get(position).getGoods_img()).into(((MyViewHolder) holder).iv);
                ((MyViewHolder) holder).zhishu.setText(list.get(position).getGoodsName());
                ((MyViewHolder) holder).price.setText("￥"+list.get(position).getShop_price());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView zhishu,price;
        ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            zhishu=itemView.findViewById(R.id.zhishu);
            price=itemView.findViewById(R.id.price);
            iv=itemView.findViewById(R.id.iv_item4);
        }
    }
}
