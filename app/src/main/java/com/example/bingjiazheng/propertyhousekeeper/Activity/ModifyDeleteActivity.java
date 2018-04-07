package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/3/30.
 */

public class ModifyDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_Title,tv_money,tv_date,tv_type,tv_address,tv_payer_payee,tv_remark;
    private Button bt_modify,bt_delete;
    private RelativeLayout iv_back;
    private SingleInfo singleInfo;
    protected List<String> data;
    private int Table;
    private String user;
    private int Life_Stage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_income_details);
        singleInfo = getIntent().getParcelableExtra("singleInfo");
        Log.e("table",""+singleInfo.get_id());
//        Bundle bundle = getIntent().getBundleExtra("data");
//        singleInfo = (SingleInfo)bundle.getParcelable("singleInfo");
//        singleInfo = getIntent().getParcelableExtra("singleInfo");
        initView();
    }
    private void initView() {
        Table = getIntent().getIntExtra("Table",0);
        user = getIntent().getStringExtra("user");
        Life_Stage = getIntent().getIntExtra("Life_Stage",0);
        tv_Title = findViewById(R.id.tv_Title);
        if(Table==Constant.Spend_db){
            tv_Title.setText("支出详情");
        }else if(Table==Constant.Income_db){
            tv_Title.setText("收入详情");
        }
        tv_money = findViewById(R.id.tv_money);
        tv_date = findViewById(R.id.tv_date);
        tv_type = findViewById(R.id.tv_type);
        tv_address = findViewById(R.id.tv_address);
        tv_payer_payee = findViewById(R.id.tv_payer_payee);
        tv_remark = findViewById(R.id.tv_remark);
        bt_modify = findViewById(R.id.bt_modify);
        bt_delete = findViewById(R.id.bt_delete);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_modify = findViewById(R.id.bt_modify);
        bt_modify.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        initView1();
    }
    private void initView1(){
        tv_money.setText(String.valueOf(singleInfo.getMoney()));
        tv_date.setText(singleInfo.getDate());
        tv_type.setText(singleInfo.getType());
        tv_address.setText(singleInfo.getAddress());
        tv_payer_payee.setText(singleInfo.getPayer_payee());
        tv_remark.setText(singleInfo.getRemark());
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                initView1();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_modify:
                Intent intent = new Intent(this,ModifySpendIncomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("singleInfo",singleInfo);
                intent.putExtras(bundle);
                intent.putExtra("Life_Stage",Life_Stage);
                if(Table== Constant.Spend_db){
                    intent.putExtra("Table",Constant.Spend_db);
                }else if(Table==Constant.Income_db){
                    intent.putExtra("Table",Constant.Income_db);
                }
                intent.putExtra("user",user);
                startActivityForResult(intent,1);
                break;
            case R.id.bt_delete:
                delete();
                break;
        }
    }
    /*private void delete(int position) {
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        SingleInfo singleInfo = (SingleInfo) listView.getItemAtPosition(position);
//        Log.e("id", singleInfo.getId() + "");
        sqLiteDatabase.execSQL("delete from " + table + " where  _id='" + singleInfo.get_id() + "'");
        *//*sqLiteDatabase.execSQL("delete from "+table+" where  user='"+singleInfo.getUser()+"' and life='"+singleInfo.getLife()+"' and money='"+singleInfo.getMoney()
                +"' and date='"+singleInfo.getDate()+"' and type='"+singleInfo.getType()+"' and address='"+singleInfo.getAddress()
                +"' and payer_payee='"+singleInfo.getPayer_payee()+"' and remark='"+singleInfo.getRemark()+"'");*//*
        sqLiteDatabase.close();
        listviewAdapter = new ListviewAdapter(this, user, Table, Life_Stage);
        listView.setAdapter(listviewAdapter);
    }*/
    private void delete() {
        MySQLiteHelper helper = DbManger.getIntance(this);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
//        SingleInfo singleInfo = (SingleInfo) listView.getItemAtPosition(position);
//        Log.e("id", singleInfo.getId() + "");
        String table = null;
        if(Table == Constant.Spend_db){
            table = "spend_db";
        }else if(Table == Constant.Income_db){
            table = "income_db";
        }
        Log.e("table",table+singleInfo.get_id());
        sqLiteDatabase.execSQL("delete from " + table + " where _id='" + singleInfo.get_id() + "'");
        sqLiteDatabase.close();
        Intent intent = new Intent();
        setResult(2,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==2){
            Intent intent = new Intent();
            setResult(2,intent);
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                singleInfo = bundle.getParcelable("singleInfo");
                handler.sendEmptyMessage(1);
            }
//            showText(this,"hello");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
