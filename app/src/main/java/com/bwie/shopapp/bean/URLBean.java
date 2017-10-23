package com.bwie.shopapp.bean;


public class URLBean {
    //服务器IP
    public static final String IP="192.168.23.144";
    //御泥坊首页
    public static String  YNF="http://m.yunifang.com/yunifang/mobile/home";
    //登录
    public static String LOGIN="http://"+IP+"/mobile/index.php?act=login";
    //注册
    public static String REGISTERM="http://"+IP+"/mobile/index.php?act=login&op=register";
    //分类   总(二,三级拼)
    public static String ER_ONE="http://"+IP+"/mobile/index.php?act=goods_class";
    //商品详情  （拼接商品cart_id）
    public static String  GOODS="http://"+IP+"/mobile/index.php?act=goods&op=goods_detail&goods_id=";
    //购物车 查询
    public static String GWC="http://"+IP+"/mobile/index.php?act=member_cart&op=cart_list";
    //添加收货地址
    public static String ADDRESS="http://"+IP+"/mobile/index.php?act=member_address&op=address_add";
    //查询收货地址
    public static String ADDRESSLIST="http://"+IP+"/mobile/index.php?act=member_address&op=address_list";
    //提交订单 第一次交互
    public static String BUY_STEP1="http://"+IP+"/mobile/index.php?act=member_buy&op=buy_step1";
    //二次交互 真正提交订单（有问题）
    public static String BUY_STEP2="http://"+IP+"/mobile/index.php?act=member_buy&op=buy_step2";
}
