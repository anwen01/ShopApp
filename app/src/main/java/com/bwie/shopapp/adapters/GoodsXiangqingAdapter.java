package com.bwie.shopapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.XiangqingBean;
import com.youth.banner.Banner;

import java.util.ArrayList;

/**
 * 作者：张玉轲
 * 时间：2017/10/12
 */

public class GoodsXiangqingAdapter extends RecyclerView.Adapter {
    private Context context;
    private XiangqingBean.DatasBean datas;

    public GoodsXiangqingAdapter(Context context, XiangqingBean.DatasBean datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }else if (position==1){
            return 1;
        }else{
            return 2;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                View view = View.inflate(context, R.layout.goodinfo1, null);
                MyViewHolder1 myViewHolder1 = new MyViewHolder1(view);
                return myViewHolder1;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1){
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i <datas.getSpec_image().size() ; i++) {
                list.add(datas.getSpec_image().get(i));
            }
            ((MyViewHolder1) holder).banner.setImages(list);
            ((MyViewHolder1) holder).banner.start();
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder{
        Banner banner;
        public MyViewHolder1(View itemView) {
            super(itemView);
            banner=itemView.findViewById(R.id.f1_banner);
        }
    }

}
