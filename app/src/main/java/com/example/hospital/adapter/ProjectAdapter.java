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

public class ProjectAdapter extends RecyclerView.Adapter <ProjectAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Project> list;
    private OnUpdateImageClickListener updateListener;
    private OnDeleteImageClickListener deleteListener;

    public ProjectAdapter(Context context, ArrayList<Project> list) {
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
        View itemView=View.inflate(context, R.layout.project_item,null);
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


        holder.tv_projID.setText(" "+projID);
        holder.tv_projName.setText(projName);
        holder.tv_depID.setText(" ->"+depID);
        holder.tv_jiage.setText(price+"元/"+unit);
        holder.tv_notes.setText(notes);


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
        private TextView tv_projID,tv_projName,tv_depID,tv_notes,tv_jiage;
        private ImageView iv_update,iv_delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_projID=(TextView)itemView.findViewById(R.id.tv_projID);
            tv_projName=(TextView)itemView.findViewById(R.id.tv_projName);
            tv_depID=(TextView)itemView.findViewById(R.id.tv_depID);
            tv_notes=(TextView)itemView.findViewById(R.id.tv_notes);
            tv_jiage=(TextView)itemView.findViewById(R.id.tv_jiage);


            iv_update=(ImageView)itemView.findViewById(R.id.iv_update);
            iv_delete=(ImageView)itemView.findViewById(R.id.iv_delete);

        }
    }
}
