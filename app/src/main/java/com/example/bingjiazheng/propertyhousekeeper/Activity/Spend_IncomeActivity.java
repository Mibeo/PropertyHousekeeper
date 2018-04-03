package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Fragment.NightModeHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/3/27.
 */

public abstract class Spend_IncomeActivity extends AppCompatActivity implements View.OnClickListener {
    protected NightModeHelper mNightModeHelper;
    protected TextView tv_time, tv_Title, tv_payer_payee;
    protected EditText et_money, et_address, et_remark, et_payer_payee;
    protected Button bt_cancel, bt_save;
    protected int mYear, mMonth, mDay;
    protected final int DATE_DIALOG = 1;
    protected Spinner spinner;
    protected LinearLayout ll;
    protected Context context;
    protected String table;
    protected MySQLiteHelper helper;
    protected SQLiteDatabase sqLiteDatabase;
    protected String user;
    protected String type;
    private RelativeLayout iv_back;
    protected List<String> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.left_to_right, R.anim.hold);
        setContentView(R.layout.activity_spend_income);
        mNightModeHelper = new NightModeHelper(this, R.style.BaseTheme);
        initview();
    }

    protected void initview() {
        /*helper = SpendManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql1);*/
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        tv_Title = findViewById(R.id.tv_Title);
        tv_payer_payee = findViewById(R.id.tv_payer_payee);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        init();
        /*tv_payer_payee.setText("收 方 : ");
        tv_Title.setText("新增支出");*/
        et_money = findViewById(R.id.et_money);
        et_address = findViewById(R.id.et_address);
        et_remark = findViewById(R.id.et_remark);
        et_payer_payee = findViewById(R.id.et_payer_payee);
        ll = (LinearLayout) findViewById(R.id.ll);
        ll.setOnClickListener(this);
        bt_save = findViewById(R.id.bt_save);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        display();
        spinner = (Spinner) findViewById(R.id.spinner);
        // 在这里两个layout自已定义效果,不用系统自带.
        // 数据源手动添加

        getDataSource();
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item, getDataSource());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(spinnerAadapter);
//        setAdapter();
        /*datas = new ArrayList<>();
        *//*for (int i = 0; i < 10; i++) {
            datas.add("项目" + i);
        }*//*
        SpinnerAdapter adapter = new SpinnerAdapter(this);
        spinner.setAdapter(adapter);
        adapter.setDatas(datas);*/
    }

    public List<String> getDataSource() {
        data = new ArrayList<>();
        getData();
        /*spinnerList.add("北京");
        spinnerList.add("上海");
        spinnerList.add("广州");
        spinnerList.add("北京");
        spinnerList.add("上海");
        spinnerList.add("广州");*/
        return data;
    }

    abstract IInterface getData();

    abstract IInterface init();

//    abstract IInterface setAdapter();

    protected void addSpendItem(String user, int life, double money, String date, String type,
                                String address, String payer_payee, String remark) {
        sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("life", life);
        contentValues.put("money", money);
        contentValues.put("date", date);
        contentValues.put("type", type);
        contentValues.put("address", address);
        contentValues.put("payer_payee", payer_payee);
        contentValues.put("remark", remark);
        sqLiteDatabase.insert(table, null, contentValues);
        sqLiteDatabase.close();
        showText(this, "保存成功");
    }

    /* protected String sql1 = "create table if not exists spend_db(user varchar(20),life integer,money decimal,time varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
     protected String sql2 = "create table if not exists income_db(id integer primary key autoincrement,user varchar(20),life integer,money decimal,time varchar(10),type varchar(10),address varchar(100),payer_payee varchar(50),remark varchar(200))";
     protected String sql4 = "create table if not exists flag_db(id integer primary key autoincrement,user varchar(20),flag varchar(200))";
     protected String sql5 = "create table if not exists budget_db(id integer primary key autoincrement,user varchar(20),life integer,type varchar(10),budget decimal,month varchar(10))";
 */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:
                showDialog(DATE_DIALOG);
                break;
            case R.id.ll:
                hideSoftInput();
                break;
            case R.id.bt_save:
                save();
                break;
            case R.id.iv_back:
            case R.id.bt_cancel:
                finish();
                break;
        }
    }

    private void save() {
        if (!et_money.getText().toString().equals("")) {
            addSpendItem(user, 1, Double.valueOf(et_money.getText().toString()), tv_time.getText().toString(), spinner.getSelectedItem() + "",
                    et_address.getText().toString(), et_payer_payee.getText().toString(), et_remark.getText().toString());
        }else{
            showText(this,"请输入金额数");
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        tv_time.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
    }

    protected DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    protected void hideSoftInput() {
        assert ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)) != null;
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(this.getCurrentFocus().
                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
