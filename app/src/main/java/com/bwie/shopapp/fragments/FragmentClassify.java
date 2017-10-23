package com.bwie.shopapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.bwie.shopapp.R;
import com.bwie.shopapp.adapters.FLGvAdapter;
import com.bwie.shopapp.adapters.FLLvOneAdapter;
import com.bwie.shopapp.adapters.FLLvTwoAdapter;
import com.bwie.shopapp.utils.UnicodeStreamForStrUtil;
import com.bwie.shopapp.bean.FLBean;
import com.bwie.shopapp.bean.URLBean;
import com.bwie.shopapp.presenter.FLPresenter;
import com.bwie.shopapp.view.FLView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/9/29
 */

public class FragmentClassify extends Fragment implements FLView {

    private ListView lv1;
    private ListView lv2;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.arg1 == 1) {
                FLBean.DatasBean.ClassListBean bean = (FLBean.DatasBean.ClassListBean) msg.obj;

                OkHttpUtils
                        .get()
                        .url(URLBean.ER_ONE + "&gc_id=" + bean.getGc_id())
                        .build()
                        .execute(new StringCallback() {

                            @Override
                            public void onError(com.squareup.okhttp.Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {

                                String str = UnicodeStreamForStrUtil.getStr(response);
                                Gson gson = new Gson();
                                FLBean bean = gson.fromJson(str, FLBean.class);
                                class_list = bean.getDatas().getClass_list();
                                lv2.setAdapter(new FLLvTwoAdapter(getActivity(), class_list));
                                getGv(URLBean.ER_ONE + "&gc_id=" + class_list.get(0).getGc_id());
                            }
                        });
            }
        }
    };
    private FLPresenter presenter;
    private GridView gv;
    private List<FLBean.DatasBean.ClassListBean> class_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classify, null);
        lv1 = view.findViewById(R.id.FL_lv);
        lv2 = view.findViewById(R.id.FL_lv2);
        gv = view.findViewById(R.id.gv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new FLPresenter(this);
        presenter.initView1();

        Log.i("hhh", "onCreate: " + 222);
    }

    @Override
    public void initView1(final List<FLBean.DatasBean.ClassListBean> list) {
        lv1.setAdapter(new FLLvOneAdapter(getActivity(), list));

        //二级默认为第一个
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URLBean.ER_ONE + "&gc_id=" + list.get(0).getGc_id())
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Gson gson = new Gson();
                final FLBean bean = gson.fromJson(str, FLBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        class_list = bean.getDatas().getClass_list();
                        lv2.setAdapter(new FLLvTwoAdapter(getActivity(), class_list));
                        getGv(URLBean.ER_ONE + "&gc_id=" + class_list.get(0).getGc_id());
                    }
                });

            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getGv(URLBean.ER_ONE + "&gc_id=" + class_list.get(i).getGc_id());
            }
        });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message message = Message.obtain();
                message.obj = list.get(i);
                message.arg1 = 1;
                handler.sendMessage(message);

            }
        });

    }


    public void getGv(String path){
        OkHttpUtils
                .get()
                .url(path)
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        String str = UnicodeStreamForStrUtil.getStr(response);
                        Gson gson = new Gson();
                        FLBean bean = gson.fromJson(str, FLBean.class);
                        List<FLBean.DatasBean.ClassListBean> class_list1 = bean.getDatas().getClass_list();
                        gv.setAdapter(new FLGvAdapter(getActivity(),class_list1));
                    }
                });
    }


}
