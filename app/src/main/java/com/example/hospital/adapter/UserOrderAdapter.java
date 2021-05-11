package com.example.hospital.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.table.Order;

import java.util.ArrayList;

public class UserOrderAdapter extends RecyclerView.Adapter <UserOrderAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Order> list;

    public UserOrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<Order> getList() {
        return list;
    }

    public void setList(ArrayList<Order> list) {
        this.list = list;
    }


    //创建ViewHolder视图
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=View.inflate(context, R.layout.user_order_item,null);
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


        holder.et_mebID.setText(mebID);
        holder.et_projID.setText(projID);
        holder.et_num.setText(num+"");
        holder.et_appotime.setText(appotime);
        holder.et_state.setText(state);

    }



    //返回数据源中数据个数
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private EditText et_mebID,et_projID,et_num,et_appotime,et_state;
        public MyViewHolder(View itemView) {
            super(itemView);
            et_mebID=(EditText)itemView.findViewById(R.id.et_mebID);
            et_projID=(EditText)itemView.findViewById(R.id.et_projID);
            et_num=(EditText)itemView.findViewById(R.id.et_num);
            et_appotime=(EditText)itemView.findViewById(R.id.et_appotime);
            et_state=(EditText)itemView.findViewById(R.id.et_state);

        }
    }
}
