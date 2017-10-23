package com.bwie.shopapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.ShopInfoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：张玉轲
 * 时间：2017/10/20
 */

public class DingdanAdapter extends BaseAdapter{
    private Context context;
    private List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list;

    public DingdanAdapter(Context context, List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list) {
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
        ViewHolder viewHolder = null;
        if (view == null) {
            view=View.inflate(context,R.layout.submitlistviewinfo,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.submitItName.setText(list.get(i).getGoods_name());
        viewHolder.submitItPrice.setText("￥"+list.get(i).getGoods_price());
        viewHolder.submitItNum.setText("购买数量 "+list.get(i).getGoods_num());
        Glide.with(context).load(list.get(i).getGoods_image_url()).into(viewHolder.submitItImg);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.submit_it_img)
        ImageView submitItImg;
        @BindView(R.id.submit_it_num)
        TextView submitItNum;
        @BindView(R.id.submit_it_name)
        TextView submitItName;
        @BindView(R.id.submit_it_price)
        TextView submitItPrice;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
