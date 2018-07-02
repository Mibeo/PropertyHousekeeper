package com.example.bingjiazheng.propertyhousekeeper.Entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bingjia.zheng on 2018/3/27.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
//    private String sql1 = "create table spend_db(user varchar(20),life integer,money decimal,data varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
//    private String sql2 = "create table income_db(id integer primary key autoincrement,user varchar(20),life integer,money decimal,data varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
//    private String sql3 = "create table password_db(user varchar(20),password varchar(20))";
//    private String sql4 = "create table flag_db(id integer primary key autoincrement,user varchar(20),life integer,flag varchar(200))";
//    private String sql5 = "create table budget_db(_id integer primary key,user varchar(20),life integer,type varchar(10),budget decimal,month varchar(10))";
//    protected String sql5 = "create table if not exists budget_db(_id Integer primary key,user varchar(20),life integer,type varchar(10),budget_money decimal,spended_money decimal,remaining_money decimal,budget_month varchar(10))";
    private String sql = "create table if not exists password_db(user varchar(20),new_password varchar(20),old_password varchar(20))";
    protected String sql2 = "create table if not exists spend_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
    protected String sql3 = "create table if not exists income_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
    private final String sql4 = "create table if not exists flag_db(_id Integer primary key,user varchar(20),life integer,date varchar(10),text varchar(400))";
    protected String sql5 = "create table if not exists budget_db(_id Integer primary key,user varchar(20),life integer,type varchar(10),budget_money decimal,spended_money decimal,budget_month varchar(10))";

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql5);
        db.execSQL("create table if not exists password_db(user varchar(20),new_password varchar(20),old_password varchar(20))");
        db.execSQL("create table if not exists spend_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))");
        db.execSQL("create table if not exists income_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))");
        db.execSQL("create table if not exists flag_db(_id Integer primary key,user varchar(20),life integer,date varchar(10),text varchar(400))");
        db.execSQL("create table if not exists budget_db(_id Integer primary key,user varchar(20),life integer,type varchar(10),budget_money decimal,spended_money decimal,budget_month varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
