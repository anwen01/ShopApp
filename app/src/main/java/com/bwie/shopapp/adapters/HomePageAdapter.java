package com.bwie.shopapp.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.HomePageBean;
import com.bwie.shopapp.utils.ImageLoaderBanner;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/8
 */

public class HomePageAdapter extends RecyclerView.Adapter {
    private Context context;
    private HomePageBean.DataBean data;

    public HomePageAdapter(Context context, HomePageBean.DataBean data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else if (position == 2) {
            return 2;
        } else if (position ==  getItemCount()- 1){
            return 4;
        }else {
            return 3;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = View.inflate(context, R.layout.homepageinfo1, null);
                MyViewHolder1 myViewHolder1 = new MyViewHolder1(view);
                return myViewHolder1;
            case 1:
                View view1 = View.inflate(context, R.layout.homepageinfo2, null);
                MyViewHolder2 myViewHolder2 = new MyViewHolder2(view1);
                return myViewHolder2;
            case 2:
                View view2 = View.inflate(context, R.layout.homepageinfo3, null);
                MyViewHolder3 myViewHolder3 = new MyViewHolder3(view2);
                return myViewHolder3;
            case 3:
                View view3 = View.inflate(context, R.layout.homepageinfo4, null);
                MyViewHolder4 myViewHolder4 = new MyViewHolder4(view3);
                return myViewHolder4;
            case 4:
                View view4 = View.inflate(context, R.layout.homepageinfo5, null);
                MyViewHolder5 myViewHolder5 = new MyViewHolder5(view4);
                return myViewHolder5;

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder1) {
            ((MyViewHolder1) holder).banner.setImageLoader(new ImageLoaderBanner());
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < data.getAd1().size(); i++) {
                list.add(data.getAd1().get(i).getImage());
            }
            ((MyViewHolder1) holder).banner.setImages(list);
            ((MyViewHolder1) holder).banner.start();

            ((MyViewHolder1) holder).banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(context, "你点击了"+data.getAd1().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (holder instanceof MyViewHolder2) {
            List<HomePageBean.DataBean.Ad5Bean> ad5 = data.getAd5();
            ((MyViewHolder2) holder).gv.setAdapter(new HomePage2Adapter(context, ad5));
        }
        if (holder instanceof MyViewHolder3) {
            List<HomePageBean.DataBean.Ad8Bean> ad8 = data.getAd8();
            ((MyViewHolder3) holder).title1.setText(ad8.get(0).getTitle());
            ((MyViewHolder3) holder).title2.setText(ad8.get(1).getTitle());
            ((MyViewHolder3) holder).title3.setText(ad8.get(2).getTitle());
            ((MyViewHolder3) holder).des1.setText(ad8.get(0).getDescription());
            ((MyViewHolder3) holder).des2.setText(ad8.get(1).getDescription());
            ((MyViewHolder3) holder).des3.setText(ad8.get(2).getDescription());
            Glide.with(context).load(ad8.get(0).getImage()).into(((MyViewHolder3) holder).iv1);
            Glide.with(context).load(ad8.get(1).getImage()).into(((MyViewHolder3) holder).iv2);
            Glide.with(context).load(ad8.get(2).getImage()).into(((MyViewHolder3) holder).iv3);

        }
        if (holder instanceof MyViewHolder4) {
            List<HomePageBean.DataBean.SubjectsBean.GoodsListBeanX> goodsList = data.getSubjects().get(position - 3).getGoodsList();
            List<HomePageBean.DataBean.SubjectsBean> subjects = data.getSubjects();
            Glide.with(context).load(subjects.get(position-3).getImage()).into(((MyViewHolder4) holder).iv);
                ((MyViewHolder4) holder).rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                ((MyViewHolder4) holder).rv.setAdapter(new HomePage4Adapter(context, goodsList));

        }

        if (holder instanceof MyViewHolder5){
            List<HomePageBean.DataBean.DefaultGoodsListBean> defaultGoodsList = data.getDefaultGoodsList();
            ((MyViewHolder5) holder).rv.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
            final HomrPage5Adapter homrPage5Adapter = new HomrPage5Adapter(context, defaultGoodsList);
            ((MyViewHolder5) holder).rv.setAdapter(homrPage5Adapter);
            homrPage5Adapter.HomePage5Listener(new HomrPage5Adapter.HomePage5Listener() {
                @Override
                public void getlistener(String url) {
                    Toast.makeText(context, "你点击了"+url, Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {

        Banner banner;

        public MyViewHolder1(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.f1_banner);

        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        GridView gv;

        public MyViewHolder2(View itemView) {
            super(itemView);
            gv = itemView.findViewById(R.id.gv);
        }
    }

    class MyViewHolder3 extends RecyclerView.ViewHolder {
        TextView title1, title2, title3, des1, des2, des3;
        ImageView iv1, iv2, iv3;

        public MyViewHolder3(View itemView) {
            super(itemView);
            title1 = itemView.findViewById(R.id.title1);
            title2 = itemView.findViewById(R.id.title2);
            title3 = itemView.findViewById(R.id.title3);
            des1 = itemView.findViewById(R.id.des1);
            des2 = itemView.findViewById(R.id.des2);
            des3 = itemView.findViewById(R.id.des3);
            iv1 = itemView.findViewById(R.id.iv1);
            iv2 = itemView.findViewById(R.id.iv2);
            iv3 = itemView.findViewById(R.id.iv3);

        }
    }

    class MyViewHolder4 extends RecyclerView.ViewHolder {
        ImageView iv;
        RecyclerView rv;

        public MyViewHolder4(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            rv = itemView.findViewById(R.id.rv);
        }
    }
    class MyViewHolder5 extends RecyclerView.ViewHolder{
        RecyclerView rv;
        public MyViewHolder5(View itemView) {
            super(itemView);
            rv=itemView.findViewById(R.id.rv);
        }
    }

    @Override
    public int getItemCount() {
        return 4 + data.getSubjects().size();
    }
}
