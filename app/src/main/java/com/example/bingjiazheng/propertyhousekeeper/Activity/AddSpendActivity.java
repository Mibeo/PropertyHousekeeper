package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.IInterface;
import com.example.bingjiazheng.propertyhousekeeper.Utils.SpendManger;

/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class AddSpendActivity extends Spend_IncomeActivity {

    protected String sql1 = "create table if not exists spend_db(user varchar(20),life integer,money decimal,time varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";

    @Override
    IInterface setVeiw() {
        helper = SpendManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql1);
        tv_payer_payee.setText("收 方 : ");
        tv_Title.setText("新增支出");
        return null;
    }

    @Override
    IInterface sets() {
        super.s = "spend_db";
        return null;
    }
}
