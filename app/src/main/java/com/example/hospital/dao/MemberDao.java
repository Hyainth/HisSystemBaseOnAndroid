package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospital.HospitalSQLiteOpenHelper;
import com.example.hospital.table.Member;

import java.util.ArrayList;

public class MemberDao {
    private SQLiteOpenHelper helper;
    public MemberDao(Context context){
        helper=new HospitalSQLiteOpenHelper(context);
    }

    public long insertMember(Member member){
        long result=-1;
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("mebID",member.getMebID());
        cv.put("mebName",member.getMebName());
        cv.put("phone",member.getPhone());
        cv.put("resdate",member.getResdate());
        cv.put("sex",member.getSex());
        result=db.insert("Member",null,cv);
        db.close();
        return result;
    }

    public int deleteMember(Member member){
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("Member", "mebID=?", new String[]{member.getMebID()});
        db.close();
        return count;
    }

    public int updateMember(Member member){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("mebName",member.getMebName());
        cv.put("phone",member.getPhone());
        cv.put("sex",member.getSex());
        int count = db.update("Member", cv, "mebID=?", new String[]{String.valueOf(member.getMebID())});
        db.close();
        return count;
    }

    public ArrayList<Member> getAllMember(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Member",null,null,null,null,null,null);
        ArrayList<Member> list=new ArrayList<Member>();
        while (cursor.moveToNext()){
            list.add(convertMember(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public Member getMemberByID(String mebID){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Member",null,"mebID=?",new String[]{mebID},null,null,null);
        Member member=new Member();
        while (cursor.moveToNext()){
            member=convertMember(cursor);
        }
        cursor.close();
        db.close();
        return member;
    }

    public ArrayList<Member> getMemberByKeyword(String keyword){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("Member",null,
                "mebID like ? or mebName like? or sex like ? or phone like ? or resdate like ?",
                new String[]{"%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%"},
                null,null,null);
        ArrayList<Member> list=new ArrayList<Member>();
        while (cursor.moveToNext()){
            list.add(convertMember(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public static Member convertMember(Cursor cursor){
        String mebID=cursor.getString(cursor.getColumnIndex("mebID"));
        String mebName=cursor.getString(cursor.getColumnIndex("mebName"));
        String sex=cursor.getString(cursor.getColumnIndex("sex"));
        String phone=cursor.getString(cursor.getColumnIndex("phone"));
        String resdate=cursor.getString(cursor.getColumnIndex("resdate"));
        return new Member(mebID,mebName,sex,phone,resdate);
    }
}
