package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.Bundle;
import android.os.IInterface;
import android.support.annotation.Nullable;

import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData2;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public class ModifySpendIncomeActivity extends Spend_IncomeActivity {
    private int i;
    private int life_stage = 1;
    protected String sql2 = "create table if not exists income_db(user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";


    @Override
    protected void getData() {
        if(i==1){
            getData1(life_stage,data);
        }else if(i==2){
            getData2(life_stage,data);
        }
    }

    @Override
    protected void init() {
        user = getIntent().getStringExtra("user");
        i = getIntent().getIntExtra("i", 0);
        singleInfo = getIntent().getParcelableExtra("singleInfo");
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql2);
        isModify = true;
        if (this.i == 1) {
            tv_payer_payee.setText("收 方 : ");
            tv_Title.setText("支出修改");
            table = "spend_db";
        } else if (this.i == 2) {
            tv_payer_payee.setText("付 方 : ");
            tv_Title.setText("收入修改");
            table = "income_db";
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }

    private void initview() {

        et_money.setText(String.valueOf(singleInfo.getMoney()));
        tv_date.setText(singleInfo.getDate());
//        spinner.set
//        spinner.setSelection();
        spinner.setSelection(0);
//        spinner.setSelection(Integer.parseInt(singleInfo.getType()));
        et_address.setText(singleInfo.getAddress());
        et_payer_payee.setText(singleInfo.getPayer_payee());
        et_remark.setText(singleInfo.getRemark());
    }
}
