package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.IInterface;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.SpinnerAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class AddSpendActivity extends Spend_IncomeActivity {

    protected String sql1 = "create table if not exists spend_db(user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
    private int life_stage = 1;
    @Override
    IInterface init() {
        user = getIntent().getStringExtra("user");
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql1);
        tv_payer_payee.setText("收 方 : ");
        tv_Title.setText("新增支出");
        super.table = "spend_db";
        return null;
    }

    @Override
    IInterface setAdapter() {
        List<String> datas = new ArrayList<>();
        switch (life_stage) {
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
    }


    private void retire(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("居家物业");
        datas.add("儿女费用");
        datas.add("衣服饰品");
        datas.add("休闲娱乐");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("人情往来");
        datas.add("学习进修");
        datas.add("医疗保健");
        datas.add("其他");
    }

    private void workmarried(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("居家物业");
        datas.add("儿女费用");
        datas.add("衣服饰品");
        datas.add("休闲娱乐");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("人情往来");
        datas.add("学习进修");
        datas.add("医疗保健");
        datas.add("其他");
    }

    private void workunmarried(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("居家物业");
        datas.add("衣服饰品");
        datas.add("休闲娱乐");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("人情往来");
        datas.add("学习进修");
        datas.add("医疗保健");
        datas.add("恋爱开销");
        datas.add("其他");
    }

    private void student(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("衣服饰品");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("休闲娱乐");
        datas.add("学习进修");
        datas.add("医疗保健");
        datas.add("恋爱开销");
        datas.add("其他");
    }
}
