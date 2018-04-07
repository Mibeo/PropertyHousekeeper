package com.example.bingjiazheng.propertyhousekeeper.Activity;

import com.example.bingjiazheng.propertyhousekeeper.ContentActivity.Spend_IncomeActivity;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData2;


/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class AddIncomeActivity extends Spend_IncomeActivity {
    protected String sql2 = "create table if not exists income_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";



    @Override
    protected void getData() {
        getData2(Life_Stage,data);
    }

    @Override
    protected void init() {
        user = getIntent().getStringExtra("user");
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql2);
        tv_payer_payee.setText("付 方 : ");
        tv_Title.setText("新增收入");
        table = "income_db";
    }




}
