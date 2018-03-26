package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.SpinnerAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Fragment.NightModeHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class AddSpendActivity extends AppCompatActivity implements View.OnClickListener {

    private NightModeHelper mNightModeHelper;
    private TextView tv_time;
    private int mYear, mMonth, mDay;
    private final int DATE_DIALOG = 1;
    private Spinner spinner;
    private LinearLayout ll;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spend);
        mNightModeHelper = new NightModeHelper(this, R.style.BaseTheme);
        initview();
    }

    private void initview() {
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        ll = (LinearLayout)findViewById(R.id.ll);
        ll.setOnClickListener(this);
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        display();
        spinner = (Spinner)findViewById(R.id.spinner);
        final List<String> datas = new ArrayList<>();
        for (int i = 0;i<10;i++){
            datas.add("项目"+i);
        }
        SpinnerAdapter adapter = new SpinnerAdapter(this);
        spinner.setAdapter(adapter);
        adapter.setDatas(datas);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_time:
                showDialog(DATE_DIALOG);
                break;
            case R.id.ll:
                hideSoftInput();
                break;
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
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
    private void hideSoftInput() {
        assert ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)) != null;
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(this.getCurrentFocus().
                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
