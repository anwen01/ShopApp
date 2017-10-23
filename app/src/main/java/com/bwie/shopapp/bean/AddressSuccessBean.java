package com.bwie.shopapp.bean;

/**
 * 作者：张玉轲
 * 时间：2017/10/19
 */

public class AddressSuccessBean {

    /**
     * code : 200
     * datas : {"address_id":6}
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
         * address_id : 6
         */

        private int address_id;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }
    }
}
