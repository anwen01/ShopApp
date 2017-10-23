package com.bwie.shopapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.FLBean;

import java.util.List;



public class FLLvOneAdapter extends BaseAdapter {
    private Context context;
    private List<FLBean.DatasBean.ClassListBean> list;

    public FLLvOneAdapter(Context context, List<FLBean.DatasBean.ClassListBean> list) {
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
        ViewHolder vh;
        if(view==null){
            view=View.inflate(context, R.layout.classifyitem1,null);

            vh=new ViewHolder();

            vh.iv=view.findViewById(R.id.fl_item1_iv);
            vh.tv=view.findViewById(R.id.fl_item1_tv);

            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }
        String image = list.get(i).getImage();

        if(image.length()<=0){
            vh.iv.setImageResource(R.mipmap.ic_launcher);
        }else {
            System.out.println(image);
            Glide.with(context).load(image).into(vh.iv);
        }

        vh.tv.setText(list.get(i).getGc_name());
        return view;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
