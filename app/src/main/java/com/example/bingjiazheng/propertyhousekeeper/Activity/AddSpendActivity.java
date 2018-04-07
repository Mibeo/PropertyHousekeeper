package com.example.bingjiazheng.propertyhousekeeper.Activity;


import com.example.bingjiazheng.propertyhousekeeper.ContentActivity.Spend_IncomeActivity;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;

import java.util.List;


/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class AddSpendActivity extends Spend_IncomeActivity {

    protected String sql1 = "create table if not exists spend_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";



    @Override
    protected void getData() {
       getData1(Life_Stage,data);
    }

    @Override
    protected void init() {
        user = getIntent().getStringExtra("user");
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql1);
        tv_payer_payee.setText("收 方 : ");
        tv_Title.setText("新增支出");
        super.table = "spend_db";
    }

}
