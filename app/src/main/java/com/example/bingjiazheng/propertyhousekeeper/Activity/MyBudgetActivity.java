package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.BudgetAdapter;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.CustomDatePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.bingjiazheng.propertyhousekeeper.Activity.DataServer.getDataSource;

/**
 * Created by bingjia.zheng on 2018/4/16.
 */

public class MyBudgetActivity extends AppCompatActivity {
    protected String sql5 = "create table if not exists budget_db(_id Integer primary key,user varchar(20),life integer,type varchar(10),budget_money decimal,spended_money decimal,remaining_money decimal,budget_month varchar(10))";

    private RelativeLayout iv_back;
    private TextView tv_Title, tv_month;
    private Spinner spinner_life;
    private String user;
    private int Life_Stage;
    private String time, date, year, month, selector_date;
    private CustomDatePicker datePicker;
    private ListView listView;
    private BudgetAdapter budgetAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_budget);
        initview();
    }

    private void initview() {
        user = getIntent().getStringExtra("user");
        Life_Stage = getIntent().getIntExtra("Life_Stage", 0);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("我的预算");
        tv_month = findViewById(R.id.tv_month);
        tv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(date);
            }
        });
        spinner_life = findViewById(R.id.spinner_life);
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item2, getDataSource());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_life.setAdapter(spinnerAadapter);
        spinner_life.setSelection(Life_Stage - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        year = time.split("-")[0];
        month = time.split("-")[1];
        tv_month.setText("预算月份 : " + year + " 年 " + month + " 月");
        selector_date = year + "-" + month;
        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_month.setText("当前月份 : " + time.split("-")[0] + " 年 " + time.split("-")[1] + " 月");
                selector_date = time.split("-")[0] + "-" + time.split("-")[1];
            }
        }, "2007-01-01 00:00", "2030-12-31 00:00");
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);
        listView = findViewById(R.id.listview);
        budgetAdapter = new BudgetAdapter(this, user, Life_Stage, selector_date);
        listView.setAdapter(budgetAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MyBudgetActivity.this, "hello", Toast.LENGTH_SHORT).show();
                Object itemAtPosition = listView.getItemAtPosition(position);
            }
        });
    }
}
