package com.bwie.shopapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopapp.DingdanActivity;
import com.bwie.shopapp.GoodsXiangQingActivity;
import com.bwie.shopapp.LoginZhuceActivity;
import com.bwie.shopapp.R;
import com.bwie.shopapp.adapters.ShopAdapter;
import com.bwie.shopapp.bean.AddShopBean;
import com.bwie.shopapp.bean.DeleteBean;
import com.bwie.shopapp.bean.ShopInfoBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/9/29
 */

public class FragmentShop extends Fragment{

    private RecyclerView rv;
    private TextView allselect;
    private TextView shop_totalprice;
    private TextView shop_totalnum;
    private TextView shop_submit;
    private String key;
    private List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list;
    private ShopAdapter shopAdapter;
    private int mTotalNum= 0;
    //总价
    private float mTotlaPrice = 0f;
    private ShopInfoBean shopInfoBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop, null);

        rv = view.findViewById(R.id.shop_recyclerview);
        //全选
        allselect = view.findViewById(R.id.shop_allselect);
        //总钱
        shop_totalprice = view.findViewById(R.id.shop_totalprice);
        //总数
        shop_totalnum = view.findViewById(R.id.shop_totalnum);
        //结算
        shop_submit = view.findViewById(R.id.shop_submit);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences preferences2 = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        key = preferences2.getString("key", "");
        showGoods();

        //1是全选中 2未全选中
        allselect.setTag(1);

        allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = (Integer) allselect.getTag();
                shopAdapter.setUnSelected(state);
                if (state==1){
                    allselect.setTag(2);
                }else{
                    allselect.setTag(1);
                }
            }
        });

        //结算
        shop_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> newlist=new ArrayList<ShopInfoBean.DatasBean.CartListBean.GoodsBean>();
                for (int i = 0; i <shopInfoBean.getDatas().getCart_list().size() ; i++) {
                    for (int j = 0; j <shopInfoBean.getDatas().getCart_list().get(i).getGoods().size() ; j++) {
                        if (shopInfoBean.getDatas().getCart_list().get(i).getGoods().get(j).isSelect()){
                            newlist.add(shopInfoBean.getDatas().getCart_list().get(i).getGoods().get(j));
                        }
                    }
                }

                if (mTotalNum==0){
                    Toast.makeText(getActivity(), "您还没有选择商品", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), DingdanActivity.class);
                    intent.putExtra("bean",(Serializable) newlist);
                    intent.putExtra("heji",mTotlaPrice);
                    getActivity().startActivity(intent);
                }
            }
        });
    }

    //展示商品
    public void showGoods(){
        RequestBody formBody=new FormBody.Builder()
                .add("key",key)
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        okhttp3.Request request=new okhttp3.Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_cart&op=cart_list")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println("==============add:"+string);
                Gson gson = new Gson();
                shopInfoBean = gson.fromJson(string, ShopInfoBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list=new ArrayList<>();
                        if (shopInfoBean.getCode()==200){
                            for (int i = 0; i < shopInfoBean.getDatas().getCart_list().size(); i++) {
                                for (int j = 0; j < shopInfoBean.getDatas().getCart_list().get(i).getGoods().size() ; j++) {
                                    list.add(shopInfoBean.getDatas().getCart_list().get(i).getGoods().get(j));
                                }
                            }
                            shopAdapter = new ShopAdapter(getActivity());
                            rv.setAdapter(shopAdapter);
                            setFirstState(list);
                            shopAdapter.setData(list);

                        }else {
                            Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                        }

                        //删除数据回调
                        shopAdapter.setOnDeleteListener(new ShopAdapter.OnDeleteListener() {
                            @Override
                            public void OnDelete(View view, int position, int cartid) {
                                getDeleData(cartid);
                            }
                        });


                        shopAdapter.setOnRefershListener(new ShopAdapter.OnRefershListener() {
                            @Override
                            public void onRefersh(boolean isSelect, List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list) {

                                    //标记底部 全选按钮
                                    if (isSelect) {
                                        Drawable left = getResources().getDrawable(R.mipmap.shopcart_selected);
                                        allselect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                                        allselect.setTag(2);
                                    } else {
                                        Drawable left = getResources().getDrawable(R.mipmap.shopcart_unselected);
                                        allselect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                                        allselect.setTag(1);
                                    }

                                    //总价
                                    mTotlaPrice = 0f;
                                    //重新赋值
                                    mTotalNum = 0;
                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).isSelect()) {
                                            Integer num = Integer.valueOf(list.get(i).getGoods_num());
                                            mTotlaPrice += Double.parseDouble(list.get(i).getGoods_price()) * num;
                                            mTotalNum += num;
                                        }
                                    }

                                    System.out.println("mTotlaPrice = " + mTotlaPrice);

                                    shop_totalprice.setText("总价 : " + mTotlaPrice);

                                    shop_totalnum.setText("共" + mTotalNum + "件商品");

                            }
                        });
                    }
                });
            }
        });

    }

    /**
     * 标记第一条数据 isfirst 1 显示商户名称 2 隐藏
     *
     * @param list
     */
    public static void setFirstState(List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list) {

        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getStore_id().equals(list.get(i - 1).getStore_id())) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                }
            }
        }

    }

    //删除
    public void getDeleData(int cart_id){
        RequestBody formBody=new FormBody.Builder()
                .add("key",key)
                .add("cart_id",cart_id+"")
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        okhttp3.Request request=new okhttp3.Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_cart&op=cart_del")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println("==============dele:"+string);
                Gson gson = new Gson();
                final DeleteBean deleteBean = gson.fromJson(string, DeleteBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (deleteBean.getCode()==200){
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else{
            SharedPreferences preferences2 = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
            key = preferences2.getString("key", "");
            showGoods();
        }
    }


}
