package com.bwie.shopapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopapp.DingdanActivity;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.AllAddressBean;
import com.bwie.shopapp.bean.DeleteBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/10/20
 */

public class AddressListAdapter extends BaseAdapter {

    private Context context;
    private List<AllAddressBean.DatasBean.AddressListBean> list;

    public AddressListAdapter(Context context, List<AllAddressBean.DatasBean.AddressListBean> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view=view.inflate(context, R.layout.addressinfo,null);
            viewHolder = new ViewHolder();
            viewHolder.dingdan_name=view.findViewById(R.id.dingdan_name);
            viewHolder.dingdan_phone=view.findViewById(R.id.dingdan_phone);
            viewHolder.dingdan_address=view.findViewById(R.id.dingdan_address);
            viewHolder.deleaddress=view.findViewById(R.id.deleaddress);
            view.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.dingdan_name.setText(list.get(i).getTrue_name());
        viewHolder.dingdan_phone.setText(list.get(i).getMob_phone());
        viewHolder.dingdan_address.setText(list.get(i).getArea_info());
        viewHolder.deleaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setDeleteListener!=null){
                    setDeleteListener.getListener(list.get(i).getAddress_id());
                }

                list.remove(i);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    class ViewHolder{
        TextView dingdan_name ;
        TextView dingdan_phone ;
        TextView dingdan_address ;
        TextView deleaddress ;
    }

    public SetDeleteListener setDeleteListener;
    public void setSetDeleteListener(SetDeleteListener setDeleteListener){
        this.setDeleteListener=setDeleteListener;
    }
    public interface SetDeleteListener{
        public void getListener(String address_id);
    }
}
