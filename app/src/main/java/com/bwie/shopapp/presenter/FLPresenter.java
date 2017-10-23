package com.bwie.shopapp.presenter;

import com.bwie.shopapp.bean.FLBean;
import com.bwie.shopapp.bean.URLBean;
import com.bwie.shopapp.model.FLModel;
import com.bwie.shopapp.view.FLView;
import com.google.gson.Gson;



public class FLPresenter {
    private FLModel model;
    private FLView view;

    public FLPresenter(FLView view) {
        this.model = new FLModel();
        this.view = view;
    }

    public void initView1(){

        model.getData(URLBean.ER_ONE, new FLModel.FLModelIs() {
            @Override
            public void isForBean(String str) {
                Gson gson = new Gson();
                FLBean bean = gson.fromJson(str, FLBean.class);
                view.initView1(bean.getDatas().getClass_list());
            }
        });


    }
}
