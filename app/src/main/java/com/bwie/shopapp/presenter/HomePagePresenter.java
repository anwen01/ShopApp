package com.bwie.shopapp.presenter;

import com.bwie.shopapp.model.HomepageModel;
import com.bwie.shopapp.utils.MyDao;
import com.bwie.shopapp.view.HomePageView;

/**
 * 作者：张玉轲
 * 时间：2017/10/9
 */

public class HomePagePresenter {
    private HomePageView homePageView;
    private HomepageModel homepageModel;

    public HomePagePresenter(HomePageView homePageView) {
        this.homePageView=homePageView;
        this.homepageModel=new HomepageModel();
    }

    public void getShow(String url){
        homepageModel.getData(url, new HomepageModel.HomepageListener() {
            @Override
            public void getListener(String str) {
                homePageView.getSuccess(str);
            }
        });
    }

}
