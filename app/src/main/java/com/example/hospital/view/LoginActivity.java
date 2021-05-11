package com.example.hospital.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hospital.R;
import com.example.hospital.dao.AccountDao;
import com.example.hospital.table.Account;
import com.example.hospital.util.GobalVar;
import com.example.hospital.util.MyTools;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText et_userID, et_pwd;
    private Button bt_login,bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        init();
    }

    private void init() {
        et_userID = (EditText) findViewById(R.id.et_userID);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_login.setOnClickListener(new loginListener());
        bt_register.setOnClickListener(new registerListener());
        Map<String, String> userData = MyTools.getData(this);
        if (userData != null) {
            et_userID.setText(userData.get("userID"));
            et_pwd.setText(userData.get("pwd"));
        }
    }

    private class registerListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this, Account_Register.class);
            startActivity(intent);
        }
    }

    private class loginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String userID = et_userID.getText().toString().trim();
            String pwd = et_pwd.getText().toString().trim();
            Account account = new Account(userID, pwd);
            int result = new AccountDao(LoginActivity.this).Login(account);
            if (result == -1) {
                Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = null;
                GobalVar.login_account = account.getUserID();
                GobalVar.user_kind = result;
                MyTools.saveData(LoginActivity.this, userID, pwd);

                if (result == 0) {
                    intent = new Intent(LoginActivity.this, Manage_Main.class);
                } else if (result == 1) {
                    GobalVar.login_account=userID;
                    intent = new Intent(LoginActivity.this, UserMainActivity.class);
                }
                startActivity(intent);
            }

        }
    }
}