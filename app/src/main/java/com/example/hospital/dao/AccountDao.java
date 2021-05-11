package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospital.HospitalSQLiteOpenHelper;
import com.example.hospital.table.Account;
import com.example.hospital.table.Member;
import com.example.hospital.util.MyTools;

public class AccountDao {
    private SQLiteOpenHelper helper;

    public AccountDao(Context context) {
        helper = new HospitalSQLiteOpenHelper(context);
    }

    public int Login(Account account) {
        int result = -1;
        String pwd = "";
        int kind = -1;

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("Account", null, "userID=?", new String[]{account.getUserID()}, null, null, null);
        while (cursor.moveToNext()) {
            pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            kind = cursor.getInt(cursor.getColumnIndex("kind"));
        }
        cursor.close();
        db.close();

        if (MyTools.getMD5(account.getPwd()).equals(pwd)) {
            result = kind;
        }
        return result;
    }

    public int updatePWD(Account account) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pwd", MyTools.getMD5(account.getPwd()));
        int count = db.update("Account", cv, "userID=?", new String[]{String.valueOf(account.getUserID())});
        db.close();
        return count;
    }

    public long registerAccount(Account account) {
        long result = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userID", account.getUserID());
        cv.put("pwd", MyTools.getMD5(account.getPwd()));
        cv.put("kind", account.getKind());
        result = db.insert("Account", null, cv);
        db.close();
        return result;
    }


    public int deleteAccount(String userID) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("Account", "userID=?", new String[]{userID});
        db.close();
        return count;
    }

    public Long registerMember(Account account,Member member){
        Long result=-1l;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        cv1.put("userID", account.getUserID());
        cv1.put("pwd", MyTools.getMD5(account.getPwd()));
        cv1.put("kind", account.getKind());
        cv2.put("mebID",member.getMebID());
        cv2.put("mebName",member.getMebName());
        cv2.put("phone",member.getPhone());
        cv2.put("resdate",member.getResdate());
        cv2.put("sex",member.getSex());
        db.beginTransaction();
        try {
            result=db.insert("Account", null, cv1);
            if (result<0){
                //手动抛出一个异常进入endTransaction
                throw new NullPointerException();
            }
            db.insert("Member",null,cv2);
            db.setTransactionSuccessful();
        }
        catch(Exception e){

        }
        finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }

    public int destroyAccount(String userID){
        int result=-1;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        db.beginTransaction();
        try {
            result=db.delete("Member","mebID=?",new String[]{userID});
            if (result<0){
                //手动抛出一个异常进入endTransaction
                throw new NullPointerException();
            }
            result=db.delete("Account","userID=?",new String[]{userID});
            db.setTransactionSuccessful();
        }
        catch(Exception e){

        }
        finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }
}
