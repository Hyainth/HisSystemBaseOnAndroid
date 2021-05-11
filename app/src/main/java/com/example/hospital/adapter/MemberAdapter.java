package com.example.hospital.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.table.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter <MemberAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Member> list;
    private OnUpdateImageClickListener updateListener;
    private OnDeleteImageClickListener deleteListener;

    public MemberAdapter(Context context, ArrayList<Member> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<Member> getList() {
        return list;
    }

    public void setList(ArrayList<Member> list) {
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
        View itemView=View.inflate(context, R.layout.member_item,null);
        return new MyViewHolder(itemView);
    }

    //绑定viewholder中的控件和数据
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Member member = list.get(position);
        String mebID=member.getMebID();
        String mebName=member.getMebName();
        String sex=member.getSex();
        String phone=member.getPhone();
        String resdate=member.getResdate();


        holder.tv_mebID.setText(" "+mebID);
        holder.tv_mebName.setText(mebName);
        holder.tv_phone.setText(phone);
        holder.tv_resdate.setText(" "+resdate);

        if(sex.equals("男"))
            holder.iv_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.img_male));
        else
            holder.iv_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.img_female));


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
        private TextView tv_mebID,tv_mebName,tv_phone,tv_resdate;
        private ImageView iv_update,iv_delete,iv_sex;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_mebID=(TextView)itemView.findViewById(R.id.tv_mebID);
            tv_mebName=(TextView)itemView.findViewById(R.id.tv_mebName);
            tv_phone=(TextView)itemView.findViewById(R.id.tv_phone);
            tv_resdate=(TextView)itemView.findViewById(R.id.tv_resdate);

            iv_update=(ImageView)itemView.findViewById(R.id.iv_update);
            iv_delete=(ImageView)itemView.findViewById(R.id.iv_delete);
            iv_sex=(ImageView)itemView.findViewById(R.id.iv_sex);

        }
    }
}
