package com.example.hospital.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.table.Department;

import java.util.ArrayList;

public class UserDepAdapter extends RecyclerView.Adapter <UserDepAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Department> list;
    private OnUpdateImageClickListener updateListener;

    public UserDepAdapter(Context context, ArrayList<Department> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<Department> getList() {
        return list;
    }

    public void setList(ArrayList<Department> list) {
        this.list = list;
    }

    //定义up监听器接口
    public interface OnUpdateImageClickListener{
        void onUpdateImageClick(View view,int position);
    }


    //给外界提供绑定监听器的方式
    public void setUpdateImageListener(OnUpdateImageClickListener listener){
        this.updateListener=listener;
    }


    //创建ViewHolder视图
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=View.inflate(context, R.layout.user_dep_item,null);
        return new MyViewHolder(itemView);
    }

    //绑定viewholder中的控件和数据
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Department department = list.get(position);
        String depID=department.getDepID();
        String depName=department.getDepName();
        String phone=department.getPhone();


        holder.et_depID.setText(depID);
        holder.et_depName.setText(depName);
        holder.et_phone.setText(phone);

        holder.iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListener.onUpdateImageClick(holder.iv_update,position);
            }
        });

    }



    //返回数据源中数据个数
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private EditText et_depID,et_depName,et_phone;
        private ImageView iv_update;
        public MyViewHolder(View itemView) {
            super(itemView);
            et_depID=(EditText)itemView.findViewById(R.id.et_depID);
            et_depName =(EditText)itemView.findViewById(R.id.et_depName);
            et_phone=(EditText)itemView.findViewById(R.id.et_phone);

            iv_update=(ImageView)itemView.findViewById(R.id.iv_update);

        }
    }
}
