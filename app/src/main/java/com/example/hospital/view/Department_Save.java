package com.example.hospital.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.dao.DepartmentDao;
import com.example.hospital.table.Department;

public class Department_Save extends AppCompatActivity {
    private DepartmentDao departmentDao;
    private EditText et_depID,et_depName,et_phone;
    private Button btn_save,btn_cancel;
    private int savemode=0;//0为增加模式，1为修改模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.department_save);

        initView();
    }

    void initView(){
        et_depID=(EditText)findViewById(R.id.et_depID);
        et_depName=(EditText)findViewById(R.id.et_depName);
        et_phone=(EditText)findViewById(R.id.et_phone);

        btn_save=(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);


        departmentDao=new DepartmentDao(Department_Save.this);

        Intent intent=getIntent();
        Department department=(Department) intent.getSerializableExtra("department");

        if(null!=department){
            savemode=1;
            et_depID.setText(department.getDepID());
            et_depName.setText(department.getDepName());
            et_phone.setText(department.getPhone());

            //设置不可变
            et_depID.setEnabled(false);
            et_depID.setBackground(Department_Save.this.getResources().getDrawable(R.drawable.disable_view));
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();

                String depID = et_depID.getText().toString().trim();
                String depName = et_depName.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();

                Department department = new Department();
                department.setDepID(depID);
                department.setDepName(depName);
                department.setPhone(phone);
                long result=-1l;
                switch (savemode) {
                    case 0:
                        result = departmentDao.insertDepartment(department);
                        if (result >0) {
                            data.putExtra("data", "ADD");
                            setResult(2, data);
                            finish();
                            return;
                        }
                        Toast.makeText(Department_Save.this, "添加失败，请检查填写情况!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        result = departmentDao.updateDepartment(department);
                        if (result >0) {
                            data.putExtra("data", "UPDATE");
                            setResult(2, data);
                            finish();
                            return;
                        }
                        Toast.makeText(Department_Save.this, "修改失败，请检查填写情况!", Toast.LENGTH_SHORT).show();
                        break;
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
            actionBar.setTitle("保存部门");
        }
    }
}
