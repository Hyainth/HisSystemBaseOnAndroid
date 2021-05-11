package com.example.hospital.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.table.Department;

import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter <DepartmentAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Department> list;
    private OnUpdateImageClickListener updateListener;
    private OnDeleteImageClickListener deleteListener;

    public DepartmentAdapter(Context context, ArrayList<Department> list) {
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
        View itemView=View.inflate(context, R.layout.department_item,null);
        return new MyViewHolder(itemView);
    }

    //绑定viewholder中的控件和数据
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Department department = list.get(position);
        String depID=department.getDepID();
        String depName=department.getDepName();
        String phone=department.getPhone();


        holder.tv_depID.setText(" "+depID);
        holder.tv_depName.setText(depName);
        holder.tv_phone.setText(" "+phone);

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
        private TextView tv_depID,tv_depName,tv_phone;
        private ImageView iv_update,iv_delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_depID=(TextView)itemView.findViewById(R.id.tv_depID);
            tv_depName =(TextView)itemView.findViewById(R.id.tv_depName);
            tv_phone=(TextView)itemView.findViewById(R.id.tv_phone);

            iv_update=(ImageView)itemView.findViewById(R.id.iv_update);
            iv_delete=(ImageView)itemView.findViewById(R.id.iv_delete);

        }
    }
}
