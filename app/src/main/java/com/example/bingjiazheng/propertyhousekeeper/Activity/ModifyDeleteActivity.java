package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;

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
    private int i;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_income_details);
        singleInfo = getIntent().getParcelableExtra("singleInfo");
//        Bundle bundle = getIntent().getBundleExtra("data");
//        singleInfo = (SingleInfo)bundle.getParcelable("singleInfo");
//        singleInfo = getIntent().getParcelableExtra("singleInfo");
        initView();
    }
    private void initView() {
        i = getIntent().getIntExtra("i",0);
        user = getIntent().getStringExtra("user");
        tv_Title = findViewById(R.id.tv_Title);
        if(i==1){
            tv_Title.setText("支出详情");
        }else if(i==2){
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
        tv_money.setText(String.valueOf(singleInfo.getMoney()));
        tv_date.setText(singleInfo.getDate());
        tv_type.setText(singleInfo.getType());
        tv_address.setText(singleInfo.getAddress());
        tv_payer_payee.setText(singleInfo.getPayer_payee());
        tv_remark.setText(singleInfo.getRemark());
        bt_modify = findViewById(R.id.bt_modify);
        bt_delete = findViewById(R.id.bt_delete);
        bt_modify.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_modify:
                Intent intent = new Intent(this,ModifySpendIncomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("singleInfo",singleInfo);
                intent.putExtras(bundle);
                if(i==1){
                    intent.putExtra("i",1);
                }else if(i==2){
                    intent.putExtra("i",2);
                }
                intent.putExtra("user",user);
                startActivityForResult(intent,1);
                break;
            case R.id.bt_delete:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==2){
            Intent intent = new Intent();
            setResult(2,intent);
            showText(this,"hello");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
