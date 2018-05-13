package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.BudgetAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.BudgetSingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.CustomDatePicker;
import com.example.bingjiazheng.propertyhousekeeper.Ui.ShowChangePassword;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getData3;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getDataSource;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.get_spend_Data;

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
    private double total_money;
    private String Table;
    private List<SingleInfo> data;
    private HashMap<String, Double> HashData = new HashMap<>();
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private HashMap<String, Double> spend_data;
    private List<String> list_key = DataServer.list_key;
    private int scrolledX;
    private int scrolledY;
    private int position1 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_budget);
        initview();
    }

    private void initview() {
        user = "123";
        Life_Stage = 1;
        Table = "spend_db";
//        user = getIntent().getStringExtra("user");
//        Life_Stage = getIntent().getIntExtra("Life_Stage", 0);
        helper = DbManger.getIntance(this);
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
        spinner_life.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) spinner_life.getItemAtPosition(position);//从spinner中获取被选择的数据
                switch (data) {
                    case "学生":
                        Life_Stage = 1;
                        budgetAdapter = new BudgetAdapter(getApplicationContext(), user, Table, Life_Stage, selector_date);
                        listView.setAdapter(budgetAdapter);
                        break;
                    case "工作未婚":
                        Life_Stage = 2;
                        budgetAdapter = new BudgetAdapter(getApplicationContext(), user, Table, Life_Stage, selector_date);
                        listView.setAdapter(budgetAdapter);
                        break;
                    case "工作已婚":
                        Life_Stage = 3;
                        budgetAdapter = new BudgetAdapter(getApplicationContext(), user, Table, Life_Stage, selector_date);
                        listView.setAdapter(budgetAdapter);
                        break;
                    case "退休":
                        Life_Stage = 4;
                        budgetAdapter = new BudgetAdapter(getApplicationContext(), user, Table, Life_Stage, selector_date);
                        listView.setAdapter(budgetAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
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
//        selector_date = "2018-05";
        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_month.setText("当前月份 : " + time.split("-")[0] + " 年 " + time.split("-")[1] + " 月");
                selector_date = time.split("-")[0] + "-" + time.split("-")[1];
                budgetAdapter = new BudgetAdapter(getApplicationContext(), user, Table, Life_Stage, selector_date);
                listView.setAdapter(budgetAdapter);
            }
        }, "2007-01-01 00:00", "2030-12-31 00:00");
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);
        listView = findViewById(R.id.listview);
        budgetAdapter = new BudgetAdapter(this, user, Table, Life_Stage, selector_date);
        listView.setAdapter(budgetAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = parent.getContext();
                final BudgetSingleInfo budgetSingleInfo = (BudgetSingleInfo) listView.getItemAtPosition(position);
                new ShowChangePassword(context, R.style.dialog, 1, budgetSingleInfo, new ShowChangePassword.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, final String editText) {
                        if (confirm) {
                            if (!editText.isEmpty()) {
                                update_budget_data(budgetSingleInfo.get_id(), user, Life_Stage, budgetSingleInfo.getType(), Double.valueOf(editText), selector_date);
                                budgetAdapter = new BudgetAdapter(getApplicationContext(), user, Table, Life_Stage, selector_date);
                                listView.setAdapter(budgetAdapter);
                                listView.setSelectionFromTop(scrolledX, scrolledY);
                                setResult(2);
                            }
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).setTitle("预算" + budgetSingleInfo.getType() + "金额").show();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            /**
             * 滚动状态改变时调用
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    try{
                        scrolledX = view.getFirstVisiblePosition();
                        scrolledY = view.getChildAt(0).getTop();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            /**
             * 滚动时调用
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
//        update_spend_remaining_data(Life_Stage, selector_date);
    }

    private void deal_data(Context context) {
        spend_data = get_spend_Data(context, user, Table, Life_Stage, selector_date);
    }


    private void update_spend_remaining_data(int Life_Stage, String selector_date) {
        total_money = 0.0;
        Table = "spend_db";
        if (data != null) {
            data.clear();
        }
        if (HashData != null) {
            HashData.clear();
        }
        data = getData3(this, Table, user, Life_Stage, selector_date);
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (HashData.containsKey(data.get(i).getType())) {
                    total_money += data.get(i).getMoney();
                    Double aDouble = HashData.get(data.get(i).getType());
                    aDouble = aDouble + data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), aDouble);
                } else {
                    total_money += data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), data.get(i).getMoney());
                }
            }
            if (!HashData.isEmpty()) {
//                addbudegetdata(user, Life_Stage, 0.0, 0.0, 0.0, selector_date);
                Iterator iterator = HashData.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
//                    update_spend_data(user, Life_Stage, String.valueOf(entry.getKey()), budget_money, total_money, 0.0, selector_date);
                    update_spended_data(user, Life_Stage, (String) entry.getKey(), (Double) entry.getValue(), selector_date);
                }
            }
        }
    }

    private void update_spended_data(String user, int life, String type, double spended_money, String budget_month) {
        Table = "budget_db";
        sqLiteDatabase = helper.getWritableDatabase();
        /*sqLiteDatabase.execSQL("update '" + Table + "'set user='" + user + "',life='" + life + "',type='" + type + "',spended_money='" + spended_money
                + "',budget_month='" + budget_month +"' where "+"_id='"+_id+"'");*/
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("life", Life_Stage);
        contentValues.put("type", type);
        contentValues.put("spended_money", spended_money);
        contentValues.put("budget_month", budget_month);
        sqLiteDatabase.update(Table, contentValues, "user=? and life=? and type=? and budget_month=?",
                new String[]{user, String.valueOf(life), type, budget_month});
        sqLiteDatabase.close();

    }

    private void update_budget_data(int _id, String user, int life, String type, double budget_money, String budget_month) {
        Table = "budget_db";
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL("update '" + Table + "'set user='" + user + "',life='" + life + "',type='" + type + "',budget_money='" + budget_money
                + "',budget_month='" + budget_month + "' where " + "_id='" + _id + "'");
        sqLiteDatabase.close();
    }
    /**
     * 更新记录
     * @param person
     */
    /*public void update(Person person){
        SQLiteDatabase db=dbOpenHelter.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name", person.getName());
        values.put("phone", person.getPhone());
        db.update("person", values,"personid=?",new String[]


                {person.getId().toString()} );*/
}
