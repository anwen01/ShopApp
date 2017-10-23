package com.bwie.shopapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.R;
import com.bwie.shopapp.bean.ShopInfoBean;
import com.bwie.shopapp.fragments.FragmentShop;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/18
 */

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list;


    public ShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list){
       if (this.list==null){
           this.list=new ArrayList<>();
       }
       this.list=list;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.group_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            if (position>0){
                if (list.get(position).getStore_id().equals(list.get(position-1).getStore_id())){
                    myViewHolder.ll.setVisibility(View.GONE);
                    System.out.println("11111111111111111");
                }else{
                    System.out.println("xianshixianshixian================="+list.get(position).getStore_id());
                    myViewHolder.ll.setVisibility(View.VISIBLE);
                }
            }else{
                myViewHolder.ll.setVisibility(View.VISIBLE);
            }


            myViewHolder.shopname.setText(list.get(position).getStore_name());
            myViewHolder.goodname.setText(list.get(position).getGoods_name());
            Glide.with(context).load(list.get(position).getGoods_image_url()).into(myViewHolder.goodspic);
            System.out.println("=======图片"+list.get(position).getGoods_image_url());
            myViewHolder.goodsprice.setText("￥"+list.get(position).getGoods_price());
            myViewHolder.goodsnum.setText(list.get(position).getGoods_num());

            //商户
            if (list.get(position).isShopSelect()){
                myViewHolder.shopselect.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_selected));

            }else{
                myViewHolder.shopselect.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_unselected));
            }
            //商品
            if (list.get(position).isSelect()){
                myViewHolder.goodselect.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_selected));
            }else{
                myViewHolder.goodselect.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_unselected));

            }
            if(onRefershListener != null){
                boolean isSelect = false;

                for(int i=0;i<list.size();i++){
                    if(!list.get(i).isSelect()){
                        isSelect = false;
                        // 只要有一个商品是 未选中的状态 ，全选按钮就是未选中
                        break;
                    }else {
                        isSelect = true;
                    }
                }
                onRefershListener.onRefersh(isSelect,list);

            }


            //商户的选中和未选中
            myViewHolder.shopselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(position).getIsFirst()==1){
                        list.get(position).setShopSelect(!list.get(position).isShopSelect());
                        for (int i = 0; i <list.size() ; i++) {
                            if (list.get(i).getStore_id().equals(list.get(position).getStore_id())){
                                list.get(i).setSelect(list.get(position).isShopSelect());
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
            });
           //商品的选中未选中
            myViewHolder.goodselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(position).setSelect(!list.get(position).isSelect());
                    for (int i = 0; i <list.size() ; i++) {
                        for (int j = 0; j <list.size() ; j++) {
                            if (list.get(j).getStore_id().equals(list.get(i).getStore_id()) && !list.get(j).isSelect()){
                                list.get(i).setShopSelect(false);
                                break;
                            }else{
                                list.get(i).setShopSelect(true);
                            }
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            //删除
            myViewHolder.goodsdele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteListener!=null){
                        Integer cartid = Integer.valueOf(list.get(position).getCart_id());
                        onDeleteListener.OnDelete(view,position,cartid);
                    }
                    list.remove(position);
                    //如果删除的是第一条数据（或者是 数据带有商户名称的数据） 更新数据源， 标记 那条数据 显示商户名称
                    FragmentShop.setFirstState(list);
                    notifyDataSetChanged();
                }
            });

        }

    }

    // 全选
    public void setUnSelected(int selected){
        if(list != null && list.size() > 0){

            for (int i=0;i<list.size();i++){
                if(selected == 2){
                    list.get(i).setSelect(false);
                    list.get(i).setShopSelect(false);
                } else {
                    list.get(i).setSelect(true);
                    list.get(i).setShopSelect(true);

                }
            }
            notifyDataSetChanged();

        }

    }

    // 商品 选中状态发生变化
    public OnRefershListener onRefershListener;
    public interface OnRefershListener{
        //isSelect true 表示商品全部选中 false 未全部选中
        void onRefersh(boolean isSelect, List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list);
    }

    public void setOnRefershListener(OnRefershListener listener){
        this.onRefershListener = listener ;
    }

    public OnDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener=onDeleteListener;
    }

    public interface OnDeleteListener{
        void OnDelete(View view, int position, int cartid);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView shopselect,goodselect,goodspic,goodsdele;
        TextView shopname,goodname,goodsprice,goodsnum;
        LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);
            shopselect=itemView.findViewById(R.id.iv_item_shopcart_shopselect);
            shopname=itemView.findViewById(R.id.tv_item_shopcart_shopname);
            goodname=itemView.findViewById(R.id.tv_item_shopcart_clothname);
            goodselect=itemView.findViewById(R.id.tv_item_shopcart_clothselect);
            goodspic=itemView.findViewById(R.id.iv_item_shopcart_cloth_pic);
            goodsprice=itemView.findViewById(R.id.tv_item_shopcart_cloth_price);
            goodsnum=itemView.findViewById(R.id.et_item_shopcart_cloth_num);
            goodsdele=itemView.findViewById(R.id.iv_item_shopcart_cloth_delete);
           ll= itemView.findViewById(R.id.ll_shopcart_header);

        }
    }


}
