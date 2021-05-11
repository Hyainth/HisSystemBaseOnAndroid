package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospital.HospitalSQLiteOpenHelper;
import com.example.hospital.table.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    private SQLiteOpenHelper helper;
    public DepartmentDao(Context context){
        helper=new HospitalSQLiteOpenHelper(context);
    }

    public long insertDepartment(Department department){
        long result=-1l;
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("depID",department.getDepID());
        cv.put("depName",department.getDepName());
        cv.put("phone",department.getPhone());
        result=db.insert("Department",null,cv);
        db.close();
        return result;
    }

    public int deleteDepartment(Department department){
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("Department", "depID=?", new String[]{department.getDepID()});
        db.close();
        return count;
    }

    public int updateDepartment(Department department){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("depName",department.getDepName());
        cv.put("phone",department.getPhone());
        int count = db.update("Department", cv, "depID=?", new String[]{String.valueOf(department.getDepID())});
        db.close();
        return count;
    }

    public ArrayList<Department> getAllDepartment(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Department",null,null,null,null,null,null);
        ArrayList list=new ArrayList<Department>();
        while (cursor.moveToNext()){
            list.add(convertDepartment(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Department> getDepartmentByKeyword(String keyword){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Department",null,
                "depID like ? or depName like? or phone like ?",
                new String[]{"%"+keyword+"%","%"+keyword+"%","%"+keyword+"%"},
                null,null,null);
        ArrayList<Department> list=new ArrayList<Department>();
        while (cursor.moveToNext()){
            list.add(convertDepartment(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public static Department convertDepartment(Cursor cursor){
        String depID=cursor.getString(cursor.getColumnIndex("depID"));
        String depName=cursor.getString(cursor.getColumnIndex("depName"));
        String phone=cursor.getString(cursor.getColumnIndex("phone"));
        return new Department(depID,depName,phone);
    }
}
