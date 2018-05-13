package com.example.bingjiazheng.propertyhousekeeper.ContentActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Fragment.NightModeHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.CustomDatePicker;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/3/27.
 */

public abstract class Spend_IncomeActivity extends AppCompatActivity implements View.OnClickListener {
    protected NightModeHelper mNightModeHelper;
    protected TextView tv_date, tv_Title, tv_payer_payee;
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
    protected boolean isModify;
    protected SingleInfo singleInfo;
    protected double old_money;
    protected String old_date;
    protected String old_type;
    protected String old_address;
    protected String old_payer_payee;
    protected String old_remark;
    protected int Life_Stage;
    protected ArrayAdapter<String> spinnerAadapter;
    private CustomDatePicker datePicker;
    private String time;
    private String date;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.left_to_right, R.anim.hold);
        setContentView(R.layout.activity_spend_income);
        mNightModeHelper = new NightModeHelper(this, R.style.BaseTheme);
        initview();
    }

    private void initview() {
        /*helper = SpendManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql1);*/
        Life_Stage = getIntent().getIntExtra("Life_Stage",0);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);
        tv_Title = findViewById(R.id.tv_Title);
        tv_payer_payee = findViewById(R.id.tv_payer_payee);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        init();
        if(isModify){
            old_money = singleInfo.getMoney();
            old_date = singleInfo.getDate();
            old_type = singleInfo.getType();
            old_address = singleInfo.getAddress();
            old_payer_payee = singleInfo.getPayer_payee();
            old_remark = singleInfo.getRemark();
        }
        /*tv_payer_payee.setText("收 方 : ");
        tv_Title.setText("新增支出");*/
        et_money = findViewById(R.id.et_money);
        et_address = findViewById(R.id.et_address);
        et_remark = findViewById(R.id.et_remark);
        et_payer_payee = findViewById(R.id.et_payer_payee);
        ll = findViewById(R.id.ll);
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
        spinner = findViewById(R.id.spinner);
        // 在这里两个layout自已定义效果,不用系统自带.
        // 数据源手动添加

        getDataSource();
        spinnerAadapter = new ArrayAdapter<String>(this,
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        //设置当前显示的日期
//        currentDate.setText(date);
        tv_date.setText(date);
        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_date.setText(time.split(" ")[0]);
//                tv_date.setText(time.split("-")[0]+"-"+time.split("-")[1]);
            }
        }, "2007-01-01 00:00", "2030-12-31 00:00");
        datePicker.showSpecificTime(true); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);
    }

    public List<String> getDataSource() {
        data = new ArrayList<>();
        getData();
        /*getData2(Life_Stage,data);*/
        return data;
    }

    protected abstract void getData();

    protected abstract void init();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
//                showDialog(DATE_DIALOG);
                datePicker.show(date);
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
            if(isModify){
                updateItem(singleInfo.get_id(),user, Life_Stage, Double.valueOf(et_money.getText().toString()), tv_date.getText().toString(), spinner.getSelectedItem() + "",
                        et_address.getText().toString(), et_payer_payee.getText().toString(), et_remark.getText().toString());
            }else {
                addSpendItem(user, Life_Stage, Double.valueOf(et_money.getText().toString()), tv_date.getText().toString(), spinner.getSelectedItem() + "",
                        et_address.getText().toString(), et_payer_payee.getText().toString(), et_remark.getText().toString());
            }
        } else {
            showText(this, "请输入金额数");
        }
    }

    private void updateItem(int _id,String user, int life, double money, String date, String type,
                            String address, String payer_payee, String remark) {
//        "delete from flag_db where user='"+user+"' and date='"+singleInfo.getDate()+"' and text='"+singleInfo.getText()+"'"
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL("update '"+table+"'set user='"+user+"',life='"+life+"',money='"+money+"',date='"+date+"',type='"+type+"',address='"+address
        +"',payer_payee='"+payer_payee+"',remark='"+remark+"' where "+"_id='"+_id+"'");
        /*sqLiteDatabase.execSQL("update '"+table+"'set user='"+user+"',life='"+life+"',money='"+money+"',date='"+date+"',type='"+type+"',address='"+address
                +"',payer_payee='"+payer_payee+"',remark='"+remark+"' where "+"_id='"+_id+"' and user='"+user+"' and life='"+life+"' and money='"+old_money+
                "' and date='"+old_date+"' and type='"+old_type+"' and address='"+old_address+"' and payer_payee='"+old_payer_payee+
                "' and remark='"+old_remark+"'");*/
        showText(this,"修改成功");
        sqLiteDatabase.close();
        SingleInfo singleInfo = new SingleInfo();
        singleInfo.set_id(_id);
        singleInfo.setUser(user);
        singleInfo.setLife(life);
        singleInfo.setMoney(money);
        singleInfo.setDate(date);
        singleInfo.setType(type);
        singleInfo.setAddress(address);
        singleInfo.setPayer_payee(payer_payee);
        singleInfo.setRemark(remark);
        Bundle bundle = new Bundle();
        bundle.putParcelable("singleInfo",singleInfo);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(2,intent);
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
        tv_date.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
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
