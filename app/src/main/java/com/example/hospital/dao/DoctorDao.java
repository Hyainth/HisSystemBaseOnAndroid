package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospital.HospitalSQLiteOpenHelper;
import com.example.hospital.table.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorDao {
    private SQLiteOpenHelper helper;
    public DoctorDao(Context context){
        helper=new HospitalSQLiteOpenHelper(context);
    }

    public long insertDoctor(Doctor doctor){
        long result=-1;
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("docID",doctor.getDocID());
        cv.put("docName",doctor.getDocName());
        cv.put("sex",doctor.getSex());
        cv.put("depID",doctor.getDepID());
        cv.put("hiredate",doctor.getHiredate());
        result=db.insert("Doctor",null,cv);
        db.close();
        return result;
    }

    public int deleteDoctor(Doctor doctor){
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("Doctor", "docID=?", new String[]{doctor.getDocID()});
        db.close();
        return count;
    }

    public int updateDoctor(Doctor doctor){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("docName",doctor.getDocName());
        cv.put("sex",doctor.getSex());
        cv.put("depID",doctor.getDepID());
        cv.put("hiredate",doctor.getHiredate());
        int count = db.update("Doctor", cv, "docID=?", new String[]{String.valueOf(doctor.getDocID())});
        db.close();
        return count;
    }

    public List<Doctor> getAllDoctor(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Doctor",null,null,null,null,null,null);
        List list=new ArrayList<Doctor>();
        while (cursor.moveToNext()){
            list.add(convertDoctor(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Doctor> getDoctorByKeyword(String keyword){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Doctor",null,
                "docID like ? or docName like? or sex like ? or depID like ? or hiredate like ?",
                new String[]{"%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%"},
                null,null,null);
        ArrayList<Doctor> list=new ArrayList<Doctor>();
        while (cursor.moveToNext()){
            list.add(convertDoctor(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public static Doctor convertDoctor(Cursor cursor){
        String docID=cursor.getString(cursor.getColumnIndex("docID"));
        String docName=cursor.getString(cursor.getColumnIndex("docName"));
        String sex=cursor.getString(cursor.getColumnIndex("sex"));
        String depID=cursor.getString(cursor.getColumnIndex("depID"));
        String hiredate=cursor.getString(cursor.getColumnIndex("hiredate"));
        return new Doctor(docID,docName,sex,depID,hiredate);
    }
}
