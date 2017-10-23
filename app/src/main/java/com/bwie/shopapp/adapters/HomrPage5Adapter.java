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

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/10
 */

public class HomrPage5Adapter extends RecyclerView.Adapter {
    private Context context;
    private List<HomePageBean.DataBean.DefaultGoodsListBean> list;
    private List<Integer> hights;

    public HomrPage5Adapter(Context context, List<HomePageBean.DataBean.DefaultGoodsListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home5item, null);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePage5Listener.getlistener(list.get(myViewHolder.getAdapterPosition()).getGoods_name());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            Glide.with(context).load(list.get(position).getGoods_img()).into(((MyViewHolder) holder).iv);
            ((MyViewHolder) holder).xiaoguo.setText(list.get(position).getEfficacy());
            ((MyViewHolder) holder).name.setText(list.get(position).getGoods_name());
            ((MyViewHolder) holder).price.setText("￥"+list.get(position).getShop_price());
            ViewGroup.LayoutParams layoutParams = ((MyViewHolder) holder).iv.getLayoutParams();
            getRandomHeight(list);
            layoutParams.height = hights.get(position);//把随机的高度赋予itemView布局
            ((MyViewHolder) holder).iv.setLayoutParams(layoutParams);//把params设置给itemView布局

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView xiaoguo,name,price;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
            xiaoguo=itemView.findViewById(R.id.xiaoguo);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }

    public HomePage5Listener homePage5Listener;
    public void HomePage5Listener(HomePage5Listener homePage5Listener){
        this.homePage5Listener=homePage5Listener;
    }

   public interface HomePage5Listener{
       public void getlistener(String url);
   }
    private void getRandomHeight(List<HomePageBean.DataBean.DefaultGoodsListBean> list){
        hights = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            hights.add((int) (200 + Math.random() * 200));
        }
    }
}
