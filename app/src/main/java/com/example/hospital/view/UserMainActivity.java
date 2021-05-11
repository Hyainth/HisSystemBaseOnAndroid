package com.example.hospital.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hospital.R;
import com.example.hospital.fragment.DepFragment;
import com.example.hospital.fragment.MebFragment;
import com.example.hospital.fragment.OrderFragment;
import com.example.hospital.fragment.ProjFragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UserMainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2,radioButton3,radioButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.user_main);

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.content_layout,new MebFragment());
        transaction.commit();

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioButton1=(RadioButton)findViewById(R.id.radioButton1);
        radioButton2=(RadioButton)findViewById(R.id.radioButton2);
        radioButton3=(RadioButton)findViewById(R.id.radioButton3);
        radioButton4=(RadioButton)findViewById(R.id.radioButton4);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();//获取事务一致性
                switch (checkedId){
                    case R.id.radioButton1:
                        transaction.replace(R.id.content_layout,new MebFragment());
                        break;
                    case R.id.radioButton2:
                        transaction.replace(R.id.content_layout,new DepFragment());
                        break;
                    case R.id.radioButton3:
                        transaction.replace(R.id.content_layout,new ProjFragment());
                        break;
                    case R.id.radioButton4:
                        transaction.replace(R.id.content_layout,new OrderFragment());
                        break;
                }
                transaction.commit();
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
            actionBar.setTitle("医院收费管理系统");
        }
    }

}
