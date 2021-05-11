package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospital.HospitalSQLiteOpenHelper;
import com.example.hospital.table.Order;
import com.example.hospital.util.MyTools;

import java.util.ArrayList;

public class OrderDao {
    private SQLiteOpenHelper helper;
    public OrderDao(Context context){
        helper=new HospitalSQLiteOpenHelper(context);
    }

    public long insertOrder(Order order){
        long result=-1;
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("orderID", MyTools.getUUID());
        cv.put("mebID",order.getMebID());
        cv.put("projID",order.getProjID());
        cv.put("num",order.getNum());
        cv.put("appotime",order.getAppotime());
        cv.put("state",order.getState());
        result=db.insert("OrderInfo",null,cv);
        db.close();
        return result;
    }

    public int deleteOrder(String orderID){
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("OrderInfo", "orderID=?", new String[]{orderID});
        db.close();
        return count;
    }

    public int updateOrder(Order order){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("mebID",order.getMebID());
        cv.put("projID",order.getProjID());
        cv.put("num",order.getNum());
        cv.put("appotime",order.getAppotime());
        cv.put("state",order.getState());
        int count = db.update("OrderInfo", cv, "orderID=?", new String[]{String.valueOf(order.getOrderID())});
        db.close();
        return count;
    }

    public ArrayList<Order> getMemberOrder(String mebID){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("OrderInfo",null,"mebID=?",new String[]{mebID},null,null,null);
        ArrayList list=new ArrayList<Order>();
        while (cursor.moveToNext()){
            list.add(convertOrder(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Order> getAllOrder(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("OrderInfo",null,null,null,null,null,null);
        ArrayList list=new ArrayList<Order>();
        while (cursor.moveToNext()){
            list.add(convertOrder(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Order> getOrderByKeyword(String keyword){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("OrderInfo",null,
                "mebID like ? or projID like? or num like ? or appotime like ? or state like ?",
                new String[]{"%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%"},
                null,null,null);
        ArrayList list=new ArrayList<Order>();
        while (cursor.moveToNext()){
            list.add(convertOrder(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    public static Order convertOrder(Cursor cursor){
        String orderID=cursor.getString(cursor.getColumnIndex("orderID"));
        String mebID=cursor.getString(cursor.getColumnIndex("mebID"));
        String projID=cursor.getString(cursor.getColumnIndex("projID"));
        int num=cursor.getInt(cursor.getColumnIndex("num"));
        String appotime=cursor.getString(cursor.getColumnIndex("appotime"));
        String state=cursor.getString(cursor.getColumnIndex("state"));
        return new Order(orderID,mebID,projID,num,appotime,state);
    }

}
