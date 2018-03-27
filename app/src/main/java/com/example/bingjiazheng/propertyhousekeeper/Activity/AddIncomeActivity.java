package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.IInterface;

import com.example.bingjiazheng.propertyhousekeeper.Utils.SpendManger;


/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class AddIncomeActivity extends Spend_IncomeActivity {
    protected String sql2 = "create table if not exists income_db(user varchar(20),life integer,money decimal,time varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
    @Override
    IInterface setVeiw() {
        helper = SpendManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql2);
        tv_payer_payee.setText("付 方 : ");
        tv_Title.setText("新增收入");
        return null;
    }

    @Override
    IInterface sets() {
        super.s = "income_db";
        return null;
    }

}
