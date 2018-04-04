package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.IInterface;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.SpinnerAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData2;


/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class AddIncomeActivity extends Spend_IncomeActivity {
    protected String sql2 = "create table if not exists income_db(user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
    private int life_stage = 1;



    @Override
    protected void getData() {
        getData2(life_stage,data);
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


    /*@Override
    IInterface setAdapter() {
        List<String> datas = new ArrayList<>();
        switch (life_stage){
            case 1:
                student(datas);
                break;
            case 2:
                workunmarried(datas);
                break;
            case 3:
                workmarried(datas);
                break;
            case 4:
                retire(datas);
                break;
        }
        SpinnerAdapter adapter = new SpinnerAdapter(this);
        spinner.setAdapter(adapter);
        adapter.setDatas(datas);
        return null;
    }*/



    private void retire(List<String> datas) {
        datas.add("退休工资");
        datas.add("养老保险");
        datas.add("儿女给予");
        datas.add("利息收入");
        datas.add("其他");
    }

    private void workmarried(List<String> datas) {
        datas.add("个人工资");
        datas.add("父母给予");
        datas.add("业余兼职");
        datas.add("股票投资");
        datas.add("利息收入");
        datas.add("其他");
    }

    private void workunmarried(List<String> datas) {
        datas.add("个人工资");
        datas.add("父母给予");
        datas.add("业余兼职");
        datas.add("股票投资");
        datas.add("利息收入");
        datas.add("其他");
    }
    private void student(List<String> datas){
        datas.add("父母给予");
        datas.add("学校奖励");
        datas.add("课外奖励");
        datas.add("其他");
    }

}
