package com.example.hospital.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.table.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter <OrderAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Order> list;
    private OnUpdateImageClickListener updateListener;
    private OnDeleteImageClickListener deleteListener;

    public OrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<Order> getList() {
        return list;
    }

    public void setList(ArrayList<Order> list) {
        this.list = list;
    }

    //定义up监听器接口
    public interface OnUpdateImageClickListener{
        void onUpdateImageClick(View view,int position);
    }


    //定义delete监听器接口
    public interface OnDeleteImageClickListener{
        void onDeleteImageClick(View view,int position);
    }

    //给外界提供绑定监听器的方式
    public void setUpdateImageListener(OnUpdateImageClickListener listener){
        this.updateListener=listener;
    }


    public void setDeleteImageClickListener(OnDeleteImageClickListener listener){
        this.deleteListener=listener;
    }


    //创建ViewHolder视图
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=View.inflate(context, R.layout.order_item,null);
        return new MyViewHolder(itemView);
    }

    //绑定viewholder中的控件和数据
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order order = list.get(position);

        String mebID=order.getMebID();
        String projID=order.getProjID();
        int num=order.getNum();
        String appotime=order.getAppotime();
        String state=order.getState();


        holder.tv_mebID.setText(mebID);
        holder.tv_projID.setText("->"+projID);
        holder.tv_num.setText(""+num);
        holder.tv_appotime.setText(appotime);
        holder.tv_state.setText(state);

        holder.iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListener.onUpdateImageClick(holder.iv_update,position);
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onDeleteImageClick(holder.iv_delete,position);
            }
        });
    }



    //返回数据源中数据个数
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_mebID,tv_projID,tv_num,tv_appotime,tv_state;
        private ImageView iv_update,iv_delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_mebID=(TextView)itemView.findViewById(R.id.tv_mebID);
            tv_projID=(TextView)itemView.findViewById(R.id.tv_projID);
            tv_num=(TextView)itemView.findViewById(R.id.tv_num);
            tv_appotime=(TextView)itemView.findViewById(R.id.tv_appotime);
            tv_state=(TextView)itemView.findViewById(R.id.tv_state);

            iv_update=(ImageView)itemView.findViewById(R.id.iv_update);
            iv_delete=(ImageView)itemView.findViewById(R.id.iv_delete);

        }
    }
}
