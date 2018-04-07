package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bingjiazheng.propertyhousekeeper.ContentActivity.Spend_IncomeActivity;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData2;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public class ModifySpendIncomeActivity extends Spend_IncomeActivity {
    private int Table;
    protected String sql2 = "create table if not exists income_db(_id Integer primary key，user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";


    @Override
    protected void getData() {
        if(Table== Constant.Spend_db){
            getData1(Life_Stage,data);
        }else if(Table==Constant.Income_db){
            getData2(Life_Stage,data);
        }
    }

    @Override
    protected void init() {
        user = getIntent().getStringExtra("user");
        Table = getIntent().getIntExtra("Table", 0);
        singleInfo = getIntent().getParcelableExtra("singleInfo");
//        helper = DbManger.getIntance(this);
//        sqLiteDatabase = helper.getWritableDatabase();
////        sqLiteDatabase.execSQL(sql2);
//        sqLiteDatabase.close();
        isModify = true;
        if (this.Table == Constant.Spend_db) {
            tv_payer_payee.setText("收 方 : ");
            tv_Title.setText("支出修改");
            table = "spend_db";
        } else if (this.Table == Constant.Income_db) {
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
        int k= spinnerAadapter.getCount();
        for(int i=0;i<k;i++){
            if(singleInfo.getType().equals(spinnerAadapter.getItem(i).toString())){
                spinner.setSelection(i,true);// 默认选中项
                break;
            }
        }
        et_address.setText(singleInfo.getAddress());
        et_payer_payee.setText(singleInfo.getPayer_payee());
        et_remark.setText(singleInfo.getRemark());
    }
}
