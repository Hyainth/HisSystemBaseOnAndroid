package com.example.hospital.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.table.Project;

import java.util.ArrayList;

public class UserProjAdapter extends RecyclerView.Adapter <UserProjAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Project> list;
    private OnUpdateImageClickListener updateListener;

    public UserProjAdapter(Context context, ArrayList<Project> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<Project> getList() {
        return list;
    }

    public void setList(ArrayList<Project> list) {
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
        View itemView=View.inflate(context, R.layout.user_project_item,null);
        return new MyViewHolder(itemView);
    }

    //绑定viewholder中的控件和数据
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Project project = list.get(position);
        String projID=project.getProjID();
        String projName=project.getProjName();
        String depID=project.getDepID();
        String unit=project.getUnit();
        String price=project.getPrice();
        String notes=project.getNotes();


        holder.et_projID.setText(projID);
        holder.et_projName.setText(projName);
        holder.et_depID.setText(depID);
        holder.et_money.setText(price+"元/"+unit);
        holder.et_notes.setText(notes);


        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListener.onUpdateImageClick(holder.iv_add,position);
            }
        });

    }



    //返回数据源中数据个数
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView et_projID,et_projName,et_depID,et_notes,et_money;
        private ImageView iv_add;
        public MyViewHolder(View itemView) {
            super(itemView);
            et_projID=(TextView)itemView.findViewById(R.id.et_projID);
            et_projName=(TextView)itemView.findViewById(R.id.et_projName);
            et_depID=(TextView)itemView.findViewById(R.id.et_depID);
            et_notes=(TextView)itemView.findViewById(R.id.et_notes);
            et_money=(TextView)itemView.findViewById(R.id.et_money);
            iv_add=(ImageView)itemView.findViewById(R.id.iv_add);

        }
    }
}
