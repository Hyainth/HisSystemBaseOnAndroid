package com.example.hospital.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.dao.AccountDao;
import com.example.hospital.table.Account;
import com.example.hospital.table.Member;
import com.example.hospital.util.MyTools;


public class Account_Register extends AppCompatActivity {
    private EditText et_userID, et_pwd, et_cpwd, et_mebName, et_phone;
    private RadioGroup rg_sex;
    private Button btn_register, btn_cancel;
    AccountDao accountDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.account_register);


        init();

    }


    private void init() {
        accountDao = new AccountDao(Account_Register.this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        et_userID = (EditText) findViewById(R.id.et_userID);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_cpwd = (EditText) findViewById(R.id.et_cpwd);
        et_mebName = (EditText) findViewById(R.id.et_mebName);
        et_phone = (EditText) findViewById(R.id.et_phone);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);


        btn_cancel.setOnClickListener(new cancelOnClickListener());
        btn_register.setOnClickListener(new registerOnClickListener());
    }

    //
    private class cancelOnClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private class registerOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String userID = et_userID.getText().toString().trim();
            String pwd = et_pwd.getText().toString().trim();
            String cpwd = et_cpwd.getText().toString().trim();
            String mebName = et_mebName.getText().toString().trim();
            String phone = et_phone.getText().toString().trim();
            String resdate = MyTools.sd.format(new java.util.Date());
            String sex = "";

            if(!pwd.equals(cpwd)){
                Toast.makeText(Account_Register.this, "两次密码输入必须一致！", Toast.LENGTH_SHORT).show();
                return;
            }

            if (R.id.rb_male == rg_sex.getCheckedRadioButtonId()) {
                sex = "男";
            }
            if (R.id.rb_female == rg_sex.getCheckedRadioButtonId()) {
                sex = "女";
            }

            Account account = new Account(userID, pwd, 1);
            Member member = new Member(userID, mebName, sex, phone, resdate);

            long result = accountDao.registerMember(account, member);
            if (result == -1) {
                Toast.makeText(Account_Register.this, "用户名已存在或输入信息格式有误，请检查！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Account_Register.this, "注册成功!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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
            actionBar.setTitle("注册会员");
        }
    }
}

