package com.bwie.shopapp.model;

import com.bwie.shopapp.utils.UnicodeStreamForStrUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 作者：张玉轲
 * 时间：2017/10/10
 */

public class FLModel {
    public void getData(final String path,final FLModelIs mis) {
        //开启网络请求
        try {
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

                            mis.isForBean(str);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public interface FLModelIs{
        void isForBean(String str);
    }
}
