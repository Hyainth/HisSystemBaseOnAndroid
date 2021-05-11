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
import com.example.hospital.dao.MemberDao;
import com.example.hospital.dao.OrderDao;
import com.example.hospital.dao.ProjectDao;
import com.example.hospital.table.Member;
import com.example.hospital.table.Order;
import com.example.hospital.table.Project;
import com.example.hospital.util.MyTools;

import java.util.ArrayList;
import java.util.Date;

public class Order_Save extends AppCompatActivity {
    private String orderID;
    private OrderDao orderDao;
    private EditText et_num,et_appotime;
    Spinner sp_mebID,sp_projID,sp_state;
    private Button btn_save,btn_cancel;
    private int savemode=0;//0为增加模式，1为修改模式，2为用户添加订单模式
    ArrayList<Member> mebItems;
    ArrayList<Project> projItems;
    ArrayList<String> stateItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inithead();
        setContentView(R.layout.order_save);

        initView();
    }

    void initView(){
        et_num=(EditText)findViewById(R.id.et_num);
        et_appotime=(EditText)findViewById(R.id.et_appotime);


        sp_mebID=(Spinner) findViewById(R.id.sp_mebID);
        sp_projID=(Spinner) findViewById(R.id.sp_projID);
        sp_state=(Spinner) findViewById(R.id.sp_state);

        btn_save=(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);


        orderDao=new OrderDao(Order_Save.this);


        mebItems = new MemberDao(Order_Save.this).getAllMember();
        ArrayAdapter<Member> mebAdapter = new ArrayAdapter<Member>(this,
                R.layout.myspinner, mebItems);
        sp_mebID.setAdapter(mebAdapter);
        sp_mebID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String sMebID = ((Member) sp_mebID.getSelectedItem()).getMebID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        projItems = new ProjectDao(Order_Save.this).getAllProject();
        ArrayAdapter<Project> projAdapter = new ArrayAdapter<Project>(this,
                R.layout.myspinner, projItems);
        sp_projID.setAdapter(projAdapter);
        sp_projID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String sProjID = ((Project) sp_projID.getSelectedItem()).getProjID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        stateItems=new ArrayList<String>();
        stateItems.add("待支付");
        stateItems.add("待就诊");
        stateItems.add("完成");
        ArrayAdapter<String> stateAdapter=new ArrayAdapter<String>(this,R.layout.myspinner,stateItems);
        sp_state.setAdapter(stateAdapter);

        et_appotime.setText(MyTools.sd.format(new Date()));




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();


                String mebID = ((Member) sp_mebID.getSelectedItem()).getMebID();
                String projID = ((Project) sp_projID.getSelectedItem()).getProjID();
                et_num=(EditText)findViewById(R.id.et_num);
                et_appotime=(EditText)findViewById(R.id.et_appotime);


                int num=Integer.valueOf(et_num.getText().toString().trim());
                String appotime=et_appotime.getText().toString().trim();
                String state=((String) sp_state.getSelectedItem());



                Order order = new Order(orderID,mebID,projID,num,appotime,state);

                long result=-1l;
                switch (savemode) {
                    case 0:
                        result = orderDao.insertOrder(order);
                        if (result >0) {
                            data.putExtra("data", "ADD");
                            setResult(2, data);
                            finish();
                            return;
                        }
                        Toast.makeText(Order_Save.this, "添加失败，请检查填写情况!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        result = orderDao.updateOrder(order);
                        if (result >0) {
                            data.putExtra("data", "UPDATE");
                            setResult(2, data);
                            finish();
                            return;
                        }
                        Toast.makeText(Order_Save.this, "修改失败，请检查填写情况!", Toast.LENGTH_SHORT).show();
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


        initData();

    }

    void initData(){
        Intent intent=getIntent();
        Order order=(Order) intent.getSerializableExtra("order");
        String mode=intent.getStringExtra("mode");
        String user_mebID=intent.getStringExtra("mebID");
        String user_projID=intent.getStringExtra("projID");

        if (null!=mode){

            if (mode.equals("user")){
                sp_projID.setEnabled(false);
                sp_mebID.setEnabled(false);
                sp_state.setEnabled(false);
            }

            if (null!=user_mebID){
                int mebPosition=0;
                for (int i = 0; i < mebItems.size(); i++) {
                    if (user_mebID.equals(mebItems.get(i).getMebID())){
                        mebPosition=i;
                        break;
                    }
                }
                sp_mebID.setSelection(mebPosition);
            }

            if (null!=user_projID){
                int projPosition=0;
                for (int i = 0; i < projItems.size(); i++) {
                    if (user_projID.equals(projItems.get(i).getProjID())){
                        projPosition=i;
                        break;
                    }
                }
                sp_projID.setSelection(projPosition);
            }

        }

        if(null!=order){
            savemode=1;
            orderID=order.getOrderID();

            et_num.setText(order.getNum()+"");
            et_appotime.setText(order.getAppotime());

            String mebID=order.getMebID();
            int mebPosition=0;
            for (int i = 0; i < mebItems.size(); i++) {
                if (mebID.equals(mebItems.get(i).getMebID())){
                    mebPosition=i;
                    break;
                }
            }
            sp_mebID.setSelection(mebPosition);

            String projID=order.getProjID();
            int ProjPosition=0;
            for (int i = 0; i < projItems.size(); i++) {
                if (projID.equals(projItems.get(i).getProjID())) {
                    ProjPosition = i;
                    break;
                }
            }
            sp_projID.setSelection(ProjPosition);

            String state=order.getState();
            int StatePosition=0;
            for (int i = 0; i < stateItems.size(); i++) {
                if (state.equals(stateItems.get(i))) {
                    ProjPosition = i;
                    break;
                }
            }
            sp_state.setSelection(mebPosition);

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
            actionBar.setTitle("保存订单");
        }
    }
}
