package com.example.hospital;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospital.util.MyTools;

public class HospitalSQLiteOpenHelper extends SQLiteOpenHelper {

    public HospitalSQLiteOpenHelper(Context context) {
        super(context, "hotest3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建立用户表
        db.execSQL("create table Account( " +
                "userID varchar(5) PRIMARY KEY, " +
                "pwd varchar(32) not null, " +
                "kind int not NULL);");

        //建立会员表
        db.execSQL("Create Table Member( " +
                "mebID varchar(5) primary key check(mebID REGEXP '^[M][0-9]{4}$'), " +
                "mebName varchar(20) not null," +
                "sex char(2) default '男' CHECK (sex in('男','女')) not null," +
                "phone varchar(15), " +
                "resdate date not null);");

        //建立科室表
        db.execSQL("Create Table Department( depID varchar(5) primary key check(depID REGEXP '^[D][0-9]{4}$'), " +
                "depName varchar(20) not null unique, " +
                "phone varchar(15));");

        //建立医生表
        db.execSQL("Create Table Doctor( " +
                "docID varchar(5) primary key check(docID REGEXP '^[Y][0-9]{4}$'), " +
                "docName varchar(20) not null, " +
                "sex char(2) default '男' CHECK (sex in('男','女')) not null, " +
                "depID varchar(5) not null, phone varchar(15), " +
                "hiredate date, " +
                "constraint FK_医生_depID foreign key(depID) references Department(depID));");

        //建立医疗项目表
        db.execSQL("Create Table Project( " +
                "projID varchar(5) primary key check(projID REGEXP '^[P][0-9]{4}$'), " +
                "projName varchar(20) not null unique, " +
                "depID varchar(5), " +
                "unit varchar(10), " +
                "price float(5,2) not null, " +
                "notes varchar(60), " +
                "constraint FK_医疗项目_depID foreign key(depID) references Department(depID));");

        //建立就诊订单表
        db.execSQL("Create Table OrderInfo( " +
                "orderID varchar(32) primary key, " +
                "mebID varchar(5), " +
                "projID varchar(5), " +
                "num int not null, " +
                "appotime date not null, " +
                "state varchar(10) not null, " +
                "constraint FK_就诊订单_mebID foreign key(mebID) references Member(mebID), " +
                "constraint FK_就诊订单_projID foreign key(projID) references Project(projID));");

        db.execSQL("Insert into Account values" +
                "('G0001','202cb962ac59075b964b07152d234b70',0)," +
                "('G0002','202cb962ac59075b964b07152d234b70',0), " +
                "('M0001','202cb962ac59075b964b07152d234b70',1), " +
                "('M0002','202cb962ac59075b964b07152d234b70',1), " +
                "('M0003','202cb962ac59075b964b07152d234b70',1), " +
                "('M0004','202cb962ac59075b964b07152d234b70',1), " +
                "('M0005','202cb962ac59075b964b07152d234b70',1), " +
                "('M0006','202cb962ac59075b964b07152d234b70',1), " +
                "('M0007','202cb962ac59075b964b07152d234b70',1), " +
                "('M0008','202cb962ac59075b964b07152d234b70',1);");

        db.execSQL("Insert into Member values" +
                "('M0001','会员1','男','13111111111','2019/11/11'), " +
                "('M0002','会员2','男','13111111112','2019/11/11'), " +
                "('M0003','会员3','女','13111111113','2019/11/11'), " +
                "('M0004','会员4','女','13111111114','2019/11/11'), " +
                "('M0005','会员5','男','13111111115','2019/11/11'), " +
                "('M0006','会员6','女','13111111116','2019/11/11'), " +
                "('M0007','会员7','女','13111111117','2019/11/11'), " +
                "('M0008','会员8','男','13111111118','2019/11/11');");

        db.execSQL("Insert into Department values" +
                "('D0001','内科','15111111111'), " +
                "('D0002','外科','15111111112'), " +
                "('D0003','眼科','15111111113'), " +
                "('D0004','急诊科','15111111114');");

        db.execSQL("Insert into Doctor values" +
                "('Y0001','医生1','男','D0001','17111111111','2016/6/18'), " +
                "('Y0002','医生2','女','D0001','17111111112','2016/6/18'), " +
                "('Y0003','医生3','男','D0002','17111111113','2016/6/18'), " +
                "('Y0004','医生4','女','D0002','17111111114','2016/6/18'), " +
                "('Y0005','医生5','男','D0003','17111111114','2016/6/18'), " +
                "('Y0006','医生6','女','D0003','17111111114','2016/6/18'), " +
                "('Y0007','医生7','男','D0004','17111111114','2016/6/18'), " +
                "('Y0008','医生8','女','D0004','17111111114','2016/6/18');");

        db.execSQL("Insert into Project values" +
                "('P0001','内科挂号','D0001','次',15,'收费为15元/次'), " +
                "('P0002','心脏检查','D0001','次',20,'收费为20元/次'), " +
                "('P0003','外科挂号','D0002','次',10,'收费为10元/次'), " +
                "('P0004','皮肤检查','D0002','次',20,'收费为20元/次'), " +
                "('P0005','眼科挂号','D0003','次',10,'收费为10元/次'), " +
                "('P0006','视力及色觉检查','D0003','次',20,'眼科检查项目'), " +
                "('P0007','急诊科挂号','D0004','次',15,'急诊费用15元/次'), " +
                "('P0008','门急诊留观诊查','D0004','日',5,'费用5元/日');");

        db.execSQL("Insert into OrderInfo values" +
                "('" + MyTools.getUUID() + "','M0001','P0001',1,'2019/12/25','待支付'), " +
                "('" + MyTools.getUUID() + "','M0001','P0002',1,'2019/12/26','待就诊'), " +
                "('" + MyTools.getUUID() + "','M0001','P0003',1,'2019/12/27','完成'), " +
                "('" + MyTools.getUUID() + "','M0001','P0004',1,'2019/12/28','待支付'), " +

                "('" + MyTools.getUUID() + "','M0002','P0003',1,'2019/12/25','待就诊'), " +
                "('" + MyTools.getUUID() + "','M0002','P0004',1,'2019/12/25','待就诊'), " +
                "('" + MyTools.getUUID() + "','M0003','P0005',1,'2019/12/25','待就诊'), " +
                "('" + MyTools.getUUID() + "','M0003','P0006',1,'2019/12/25','待就诊'), " +
                "('" + MyTools.getUUID() + "','M0004','P0007',1,'2019/12/1','完成'), " +
                "('" + MyTools.getUUID() + "','M0004','P0008',10,'2019/12/1','完成');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
