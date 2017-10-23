package com.bwie.shopapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.NoAnimation;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.SouSuoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：张玉轲
 * 时间：2017/10/20
 */

public class GoodsDingdanAdapter extends BaseAdapter {
    private Context context;
    private String tu;
    private String name;
    private String price;
    private String num;

    public GoodsDingdanAdapter(Context context, String tu, String name, String price, String num) {
        this.context = context;
        this.tu = tu;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view == null) {
            view = View.inflate(context, R.layout.submitlistviewinfo, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Glide.with(context).load(tu).into(viewHolder.submitItImg);
        viewHolder.submitItName.setText(name);
        viewHolder.submitItNum.setText("数量"+num);
        Integer num1 = Integer.valueOf(num);
        double price1 = Double.parseDouble(price);
        double zprice = num1 * price1;
        viewHolder.submitItPrice.setText("￥"+zprice);
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
