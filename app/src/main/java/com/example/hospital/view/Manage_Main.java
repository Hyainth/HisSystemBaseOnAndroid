package com.example.hospital.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;


public class Manage_Main extends AppCompatActivity {
    private ImageView iv_meb,iv_dep,iv_proj,iv_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_main);


        inithead();

        iv_meb=(ImageView)findViewById(R.id.iv_meb);
        iv_dep=(ImageView)findViewById(R.id.iv_dep);
        iv_proj=(ImageView)findViewById(R.id.iv_proj);
        iv_order=(ImageView)findViewById(R.id.iv_order);

        iv_meb.setOnClickListener(new MainButtonClickListener());
        iv_dep.setOnClickListener(new MainButtonClickListener());
        iv_proj.setOnClickListener(new MainButtonClickListener());
        iv_order.setOnClickListener(new MainButtonClickListener());
    }

    protected class MainButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            switch(v.getId()){
                case R.id.iv_meb:
                    intent=new Intent(Manage_Main.this,Member_Main.class);
                    startActivity(intent);
                    break;
            }
            switch(v.getId()){
                case R.id.iv_dep:
                    intent=new Intent(Manage_Main.this,Department_Main.class);
                    startActivity(intent);
                    break;
            }
            switch(v.getId()){
                case R.id.iv_proj:
                    intent=new Intent(Manage_Main.this,Project_Main.class);
                    startActivity(intent);
                    break;
            }
            switch(v.getId()){
                case R.id.iv_order:
                    intent=new Intent(Manage_Main.this, Order_Main.class);
                    startActivity(intent);
                    break;
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
            actionBar.setTitle("管理界面");
        }
    }
}
