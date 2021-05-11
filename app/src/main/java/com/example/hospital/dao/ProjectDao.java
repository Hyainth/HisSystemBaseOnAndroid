package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospital.HospitalSQLiteOpenHelper;
import com.example.hospital.table.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectDao {
    private SQLiteOpenHelper helper;
    public ProjectDao(Context context){
        helper=new HospitalSQLiteOpenHelper(context);
    }

    public long insertProject(Project project){
        long result=-1;
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("projID",project.getProjID());
        cv.put("projName",project.getProjName());
        cv.put("depID",project.getDepID());
        cv.put("unit",project.getUnit());
        cv.put("price",project.getPrice());
        cv.put("notes",project.getNotes());
        result=db.insert("Project",null,cv);
        db.close();
        return result;
    }

    public int deleteProject(Project project){
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("Project", "projID=?", new String[]{project.getProjID()});
        db.close();
        return count;
    }

    public int updateProject(Project project){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("projName",project.getProjName());
        cv.put("depID",project.getDepID());
        cv.put("unit",project.getUnit());
        cv.put("price",project.getPrice());
        cv.put("notes",project.getNotes());
        int count = db.update("Project", cv, "projID=?", new String[]{String.valueOf(project.getProjID())});
        db.close();
        return count;
    }

    public ArrayList<Project> getAllProject(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Project",null,null,null,null,null,null);
        ArrayList<Project> list=new ArrayList<Project>();
        while (cursor.moveToNext()){
            list.add(convertProject(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Project> getProjectByKeyword(String keyword){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Project",null,
                "projID like ? or projName like? or depID like ? or unit like ? or price like ? or notes like ?",
                new String[]{"%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%"},
                null,null,null);
        ArrayList<Project> list=new ArrayList<Project>();
        while (cursor.moveToNext()){
            list.add(convertProject(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public static Project convertProject(Cursor cursor){
        String projID=cursor.getString(cursor.getColumnIndex("projID"));
        String projName=cursor.getString(cursor.getColumnIndex("projName"));
        String depID=cursor.getString(cursor.getColumnIndex("depID"));
        String unit=cursor.getString(cursor.getColumnIndex("unit"));
        String price=cursor.getString(cursor.getColumnIndex("price"));
        String notes=cursor.getString(cursor.getColumnIndex("notes"));
        return new Project(projID,projName,depID,unit,price,notes);
    }
}
