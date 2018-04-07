package com.example.bingjiazheng.propertyhousekeeper.Activity;

import com.example.bingjiazheng.propertyhousekeeper.ContentActivity.SpendIncomeContentActivity;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public class MyIncomeActivity extends SpendIncomeContentActivity {
    private String sql2 = "create table if not exists income_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";

    @Override
    protected void init() {
        tv_Title.setText("我的收入");
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.close();
        super.Table= Constant.Income_db;
        super.table = "income_db";
    }
}
