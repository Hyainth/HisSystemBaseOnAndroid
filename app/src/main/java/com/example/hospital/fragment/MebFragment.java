package com.example.hospital.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hospital.R;
import com.example.hospital.dao.MemberDao;
import com.example.hospital.table.Member;
import com.example.hospital.util.GobalVar;
import com.example.hospital.view.Member_Save;
import com.example.hospital.view.User_ChangePWD;

public class MebFragment extends Fragment {
    private TextView et_mebID,et_mebName,et_phone,et_resdate;
    private MemberDao memberDao;
    private ImageView iv_sex;
    private Member member;
    private ImageView iv_update,iv_pwd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.meb_fragment,null);


        et_mebID=(TextView)view.findViewById(R.id.et_mebID);
        et_mebName=(TextView)view.findViewById(R.id.et_mebName);
        et_phone=(TextView)view.findViewById(R.id.et_phone);
        et_resdate=(TextView)view.findViewById(R.id.et_resdate);
        iv_sex=(ImageView)view.findViewById(R.id.iv_sex);
        iv_update=(ImageView)view.findViewById(R.id.iv_update);
        iv_pwd=(ImageView)view.findViewById(R.id.iv_pwd);

        memberDao=new MemberDao(getActivity());


        setMember();

        iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Member_Save.class);
                //requestCode请求码:标识其他页面会送的数据是针对哪一条请求的
                intent.putExtra("member",member);
                startActivityForResult(intent,1);
            }
        });

        iv_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), User_ChangePWD.class);
                startActivity(intent);
            }
        });



        return view;
    }

    void setMember(){
        member=memberDao.getMemberByID(GobalVar.login_account);
        et_mebID.setText(member.getMebID());
        et_mebName.setText(member.getMebName());
        et_phone.setText(member.getPhone());
        et_resdate.setText(member.getResdate());

        if(member.getSex().equals("男"))
            iv_sex.setImageDrawable(getContext().getResources().getDrawable(R.drawable.img_male));
        else
            iv_sex.setImageDrawable(getContext().getResources().getDrawable(R.drawable.img_female));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断requestcode的数值
        if ( requestCode==1 && resultCode==2 ){
            //跟PutExtra相反的要使用getExtra来获取数据
            String result=data.getStringExtra("data");
            //文本框显示回传的数据的内容
            if (result.equals("SUCCESS")){
                setMember();
            }
        }
    }
}
