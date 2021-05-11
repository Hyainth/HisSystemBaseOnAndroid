package com.example.hospital.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.dao.MemberDao;
import com.example.hospital.table.Member;

public class Member_Save extends AppCompatActivity {
    private MemberDao memberDao;
    private EditText et_mebID,et_mebName,et_phone;
    private Button btn_save,btn_cancel;
    private RadioGroup rg_sex;
    private RadioButton rb_male,rb_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.member_save);

        initView();
    }

    void initView(){
        et_mebID=(EditText)findViewById(R.id.et_mebID);
        et_mebName=(EditText)findViewById(R.id.et_mebName);
        et_phone=(EditText)findViewById(R.id.et_phone);

        btn_save=(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        rg_sex=(RadioGroup)findViewById(R.id.rg_sex);
        rb_male=(RadioButton)findViewById(R.id.rb_male);
        rb_female=(RadioButton)findViewById(R.id.rb_female);

        memberDao=new MemberDao(Member_Save.this);

        Intent intent=getIntent();
        Member member=(Member) intent.getSerializableExtra("member");

        if(null!=member){
            et_mebID.setText(member.getMebID());
            et_mebName.setText(member.getMebName());
            et_phone.setText(member.getPhone());

            String sex=member.getSex();
            if (sex.equals("男")){
                rb_male.setChecked(true);
            }else if (sex.equals("女")){
                rb_female.setChecked(true);
            }
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();

                String mebID = et_mebID.getText().toString().trim();
                String mebName = et_mebName.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String sex = "";

                if (R.id.rb_male == rg_sex.getCheckedRadioButtonId()) {
                    sex = "男";
                }
                if (R.id.rb_female == rg_sex.getCheckedRadioButtonId()) {
                    sex = "女";
                }

                Member member = new Member();
                member.setMebID(mebID);
                member.setMebName(mebName);
                member.setPhone(phone);
                member.setSex(sex);

                int result=memberDao.updateMember(member);
                if(result==1){
                    data.putExtra("data","SUCCESS");
                    setResult(2,data);
                    finish();
                    return;
                }
                Toast.makeText(Member_Save.this,"修改失败，请检查填写情况!",Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void inithead(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("保存会员");
        }
    }
}
