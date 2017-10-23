package com.bwie.shopapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwie.shopapp.R;

import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/11
 */

public class SousuoAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public SousuoAdapter(Context context, List<String> list) {
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
            view=View.inflate(context, R.layout.sousuolistviewitem,null);
            myViewHolder = new MyViewHolder();
            myViewHolder.name=view.findViewById(R.id.tv_name);
            view.setTag(myViewHolder);
        }else{
            myViewHolder= (MyViewHolder) view.getTag();
        }
        myViewHolder.name.setText(list.get(i));
        return view;
    }

    class MyViewHolder{
        TextView name;
    }
}
