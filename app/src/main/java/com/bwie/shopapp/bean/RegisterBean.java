package com.bwie.shopapp.bean;

/**
 * 作者：张玉轲
 * 时间：2017/10/12
 */

public class RegisterBean {

    /**
     * code : 200
     * datas : {"key":"f5b6ff1f2c7403b4f5cb990c67ec251b","userid":"2","username":"1234567"}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * key : f5b6ff1f2c7403b4f5cb990c67ec251b
         * userid : 2
         * username : 1234567
         */

        private String key;
        private String userid;
        private String username;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
