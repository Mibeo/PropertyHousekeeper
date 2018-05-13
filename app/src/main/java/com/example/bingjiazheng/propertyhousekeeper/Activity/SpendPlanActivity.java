package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.BudgetSingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.CustomDatePicker;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getData3;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getDataSource;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.get_budget_data;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.utils.setListViewHeightBasedOnChildren;

/**
 * Created by bingjia.zheng on 2018/4/13.
 */

public class SpendPlanActivity extends AppCompatActivity {
    private String sql1 = "create table if not exists spend_db(_id Integer primary key,user varchar(20),life integer,money decimal,date varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
    private TextView tv_Title,tv_suggest;
    private Spinner spinner_life;
    private int Life_Stage;
    private RelativeLayout iv_back;
    private LinearLayout ll_month;
    private TextView tv_month, tv_month_setting;
    private String time, date, year, month, selector_date;
    private CustomDatePicker datePicker;
    private String Table;
    private List<SingleInfo> data;
    private Map<String, Double> HashData = new HashMap<>();
    private String user;
    private ListView listView;
    private ListviewAdapter listviewAdapter;
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private List<BudgetSingleInfo> budget_data = new ArrayList<>();
    private List<String> list_key = DataServer.list_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_plan);
        initview();
    }

    private void initview() {
//        user = getIntent().getStringExtra("user");
//        Life_Stage = getIntent().getIntExtra("Life_Stage",0);
        user = "123";
        Life_Stage = 1;
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.close();
        HashData = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        year = time.split("-")[0];
        month = time.split("-")[1];
        selector_date = year + "-" + month;
        Log.e("selector_date", selector_date);
        tv_month = findViewById(R.id.tv_month);
        tv_month.setText(year + " 年 " + month + " 月");
        tv_month_setting = findViewById(R.id.tv_month_setting);
        tv_month_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MyBudgetActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("Life_Stage", Life_Stage);
//                startActivity(intent);
                startActivityForResult(intent,1);
            }
        });
        ll_month = findViewById(R.id.ll_month);
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_month.setText(time.split("-")[0] + " 年 " + time.split("-")[1] + " 月");
                selector_date = time.split("-")[0] + "-" + time.split("-")[1];
                setData(getApplicationContext(), Life_Stage, selector_date);
            }
        }, "2007-01-01 00:00", "2030-12-31 00:00");
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);
        ll_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(date);
            }
        });
        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("支出规划");
        tv_suggest = findViewById(R.id.tv_suggest);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner_life = findViewById(R.id.spinner_life);
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item2, getDataSource());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_life.setAdapter(spinnerAadapter);
        spinner_life.setSelection(Life_Stage - 1);
        spinner_life.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) spinner_life.getItemAtPosition(position);//从spinner中获取被选择的数据
                switch (data) {
                    case "学生":
                        Life_Stage = 1;
                        setData(getApplicationContext(), Life_Stage, selector_date);
                        break;
                    case "工作未婚":
                        Life_Stage = 2;
                        setData(getApplicationContext(), Life_Stage, selector_date);
                        break;
                    case "工作已婚":
                        Life_Stage = 3;
                        setData(getApplicationContext(), Life_Stage, selector_date);
                        break;
                    case "退休":
                        Life_Stage = 4;
                        setData(getApplicationContext(), Life_Stage, selector_date);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        listView = findViewById(R.id.listview);
        setData(this, Life_Stage, selector_date);
    }

    private void setData(Context context, int Life_Stage, String selector_date) {
        listviewAdapter = new ListviewAdapter(this, user, Life_Stage, selector_date);
        listView.setAdapter(listviewAdapter);
        listView.setFocusable(false);
        setListViewHeightBasedOnChildren(listView);
        if (!HashData.isEmpty()) {
            HashData.clear();
        }
        if (!budget_data.isEmpty()) {
            budget_data.clear();
        }
        HashData = DataServer.get_spend_Data(context, user, "spend_db", Life_Stage, selector_date);
        budget_data = get_budget_data(context,user,Life_Stage,selector_date);
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < budget_data.size(); i++) {
            for (int j = 0; j < HashData.size(); j++) {
                if(list_key.get(j).equals(budget_data.get(i).getType())){
                    double spend_money = HashData.get(list_key.get(j));
                    double budget_money = budget_data.get(i).getBudget_money();
                    if(budget_money!=0){
                        if(budget_money<spend_money){
                            stringBuffer.append(list_key.get(j)+" : 支出超额，请控制此方面的消费\n");
                        }
                    }
                }
            }
        }
        tv_suggest.setText(stringBuffer);
    }

    private void spend_data_deal(int Life_Stage, String selector_date) {
        Table = "spend_db";
        data.clear();
        HashData.clear();
        data = getData3(this, Table, user, Life_Stage, selector_date);
        for (int i = 0; i < data.size(); i++) {
            if (HashData.containsKey(data.get(i).getType())) {
                Double aDouble = HashData.get(data.get(i).getType());
                aDouble = aDouble + data.get(i).getMoney();
                HashData.put(data.get(i).getType(), aDouble);
            } else {
                HashData.put(data.get(i).getType(), data.get(i).getMoney());
            }
        }
        Iterator iterator = HashData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
        }
    }

    @Override
    protected void onDestroy() {
        listviewAdapter.notifyDataSetChanged();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&& resultCode == 2){
            setData(getApplicationContext(),Life_Stage,selector_date);
        }
    }
}
