package com.bwie.shopapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.FLBean;

import java.util.List;



public class FLGvAdapter extends BaseAdapter {

    private Context context;
    private List<FLBean.DatasBean.ClassListBean> list;

    public FLGvAdapter(Context context, List<FLBean.DatasBean.ClassListBean> list) {
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
            view=View.inflate(context, R.layout.classifyitem3,null);

            vh=new ViewHolder();

            vh.tv=view.findViewById(R.id.fl_item3_tv);

            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }
        vh.tv.setText(list.get(i).getGc_name());
        return view;
    }

    class ViewHolder{
        TextView tv;
    }
}
