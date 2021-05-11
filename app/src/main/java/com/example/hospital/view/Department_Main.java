package com.example.hospital.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.DepartmentAdapter;
import com.example.hospital.dao.DepartmentDao;
import com.example.hospital.table.Department;
import com.example.hospital.util.RecycleViewDivider;


public class Department_Main extends AppCompatActivity {

    private DepartmentDao departmentDao;
    private DepartmentAdapter adapter;
    private RecyclerView rv;
    private EditText et_keyword;
    private Button btn_select;
    private ImageView img_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.other_main);
        initView();

        departmentDao=new DepartmentDao(Department_Main.this);
        adapter=new DepartmentAdapter(Department_Main.this,departmentDao.getAllDepartment());
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new RecycleViewDivider(Department_Main.this, LinearLayoutManager.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(Department_Main.this,LinearLayoutManager.VERTICAL,false));

        //通过adapter设置iv的监听器
        adapter.setUpdateImageListener(new DepartmentAdapter.OnUpdateImageClickListener() {
            @Override
            public void onUpdateImageClick(View view, int position) {
                Department department=adapter.getList().get(position);
                Intent intent=new Intent(Department_Main.this, Department_Save.class);
                //requestCode请求码:标识其他页面会送的数据是针对哪一条请求的
                intent.putExtra("department",department);
                startActivityForResult(intent,1);
            }
        });

        adapter.setDeleteImageClickListener(new DepartmentAdapter.OnDeleteImageClickListener() {
            @Override
            public void onDeleteImageClick(View view, final int position) {
                //确定删除对话框
                final AlertDialog.Builder builder=new AlertDialog.Builder(Department_Main.this);
                final Department department=adapter.getList().get(position);
                builder.setTitle("确定要删除部门"+department.getDepID()+"吗？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //从库中删除
                        if(departmentDao.deleteDepartment(department)>0){
                            //获取对象
                            adapter.getList().remove(position);
                            //通知系统有删除，实现动画
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, adapter.getList().size() - position);
                        }
                    }
                });
                builder.show();
            }
        });


    }

    void initView() {
        rv=(RecyclerView)findViewById(R.id.rv);
        et_keyword=(EditText)findViewById(R.id.et_keyword);
        btn_select=(Button)findViewById(R.id.btn_select);
        img_add=(ImageView)findViewById(R.id.img_add);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword=et_keyword.getText().toString().trim();
                //更改适配器中list指向空间
                adapter.setList(departmentDao.getDepartmentByKeyword(keyword));
                adapter.notifyDataSetChanged();
            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Department_Main.this, Department_Save.class);
                //requestCode请求码:标识其他页面会送的数据是针对哪一条请求的
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    //这个方法的作用是接收和处理其他页面返回的数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断requestcode的数值
        if ( requestCode==1 && resultCode==2 ){
            //跟PutExtra相反的要使用getExtra来获取数据
            String result=data.getStringExtra("data");
            //文本框显示回传的数据的内容
            if (result.equals("UPDATE") || result.equals("ADD")){
                adapter.setList(departmentDao.getDepartmentByKeyword(et_keyword.getText().toString().trim()));
                adapter.notifyDataSetChanged();
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
            actionBar.setTitle("部门管理");
        }
    }
}
