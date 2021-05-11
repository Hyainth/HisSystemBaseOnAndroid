package com.example.hospital.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.dao.DepartmentDao;
import com.example.hospital.dao.ProjectDao;
import com.example.hospital.table.Department;
import com.example.hospital.table.Project;

import java.util.ArrayList;

public class Project_Save extends AppCompatActivity {
    private ProjectDao projectDao;
    private EditText et_projID,et_projName,et_unit,et_price,et_notes;
    Spinner sp_depID;
    private Button btn_save,btn_cancel;
    private int savemode=0;//0为增加模式，1为修改模式
    ArrayList<Department> spItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.project_save);

        initView();
    }

    void initView(){
        et_projID=(EditText)findViewById(R.id.et_projID);
        et_projName=(EditText)findViewById(R.id.et_projName);
        et_unit=(EditText)findViewById(R.id.et_unit);
        et_price=(EditText)findViewById(R.id.et_price);
        et_notes=(EditText)findViewById(R.id.et_notes);
        sp_depID=(Spinner) findViewById(R.id.sp_depID);

        btn_save=(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);


        projectDao=new ProjectDao(Project_Save.this);


        spItems = new DepartmentDao(Project_Save.this).getAllDepartment();
        ArrayAdapter<Department> myaAdapter = new ArrayAdapter<Department>(this,
                R.layout.myspinner, spItems);
        sp_depID.setAdapter(myaAdapter);
        sp_depID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String sDepID = ((Department) sp_depID.getSelectedItem()).getDepID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });





        Intent intent=getIntent();
        Project project=(Project) intent.getSerializableExtra("project");

        if(null!=project){
            savemode=1;
            et_projID.setText(project.getProjID());
            et_projName.setText(project.getProjName());
            et_unit.setText(project.getUnit());
            et_price.setText(project.getPrice());
            et_notes.setText(project.getNotes());

            String depID=project.getDepID();
            int position=0;
            for (int i = 0; i < spItems.size(); i++) {
                if (depID.equals(spItems.get(i).getDepID()))
                    position=i;
            }
            sp_depID.setSelection(position);

            //设置不可变
            et_projID.setEnabled(false);
            et_projID.setBackground(Project_Save.this.getResources().getDrawable(R.drawable.disable_view));
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();

                String projID=et_projID.getText().toString().trim();
                String projName=et_projName.getText().toString().trim();
                String price=et_price.getText().toString().trim();
                String unit=et_unit.getText().toString().trim();
                String notes=et_notes.getText().toString().trim();

                String depID = ((Department) sp_depID.getSelectedItem()).getDepID();

                Project project = new Project(projID,projName,depID,unit,price,notes);

                long result=-1l;
                switch (savemode) {
                    case 0:
                        result = projectDao.insertProject(project);
                        if (result >0) {
                            data.putExtra("data", "ADD");
                            setResult(2, data);
                            finish();
                            return;
                        }
                        Toast.makeText(Project_Save.this, "添加失败，请检查填写情况!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        result = projectDao.updateProject(project);
                        if (result >0) {
                            data.putExtra("data", "UPDATE");
                            setResult(2, data);
                            finish();
                            return;
                        }
                        Toast.makeText(Project_Save.this, "修改失败，请检查填写情况!", Toast.LENGTH_SHORT).show();
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
            actionBar.setTitle("保存项目");
        }
    }
}
