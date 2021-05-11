package com.example.hospital.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.dao.AccountDao;
import com.example.hospital.table.Account;
import com.example.hospital.util.GobalVar;

public class User_ChangePWD extends AppCompatActivity {
    private TextView tv_userID;
    private EditText et_oldpwd,et_newpwd;
    private Button btn_save,btn_cancel;
    private AccountDao accountDao;
    private Account account;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.user_update_pwd);


        accountDao=new AccountDao(User_ChangePWD.this);

        tv_userID=(TextView)findViewById(R.id.tv_userID);
        et_oldpwd=(EditText)findViewById(R.id.et_oldpwd);
        et_newpwd=(EditText)findViewById(R.id.et_newpwd);
        btn_save=(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);


        tv_userID.setText(GobalVar.login_account);



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=GobalVar.login_account;
                String oldpwd=et_oldpwd.getText().toString().trim();
                String newpwd=et_newpwd.getText().toString().trim();

                account=new Account(userID,oldpwd);
                if (accountDao.Login(account)==1){
                    account.setPwd(newpwd);
                    if(accountDao.updatePWD(account)>0){
                        Toast.makeText(User_ChangePWD.this,"修改成功!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(User_ChangePWD.this,"密码错误!",Toast.LENGTH_SHORT).show();
                }
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
            actionBar.setTitle("修改密码");
        }
    }
}
