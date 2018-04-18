package com.example.bingjiazheng.propertyhousekeeper.Ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Entity.BudgetSingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class ShowChangePassword extends Dialog implements View.OnClickListener {
    private EditText editText;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;
    private int inputType;
private BudgetSingleInfo budgetSingleInfo;
    private Context mContext;
    private String ip;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private int i;

    public ShowChangePassword(Context context, int thenmResId, OnCloseListener listener) {
        super(context, thenmResId);
        this.mContext = context;
        this.listener = listener;
    }

    public ShowChangePassword(Context context, int thenmResId, int inputType, BudgetSingleInfo budgetSingleInfo,OnCloseListener listener) {
        super(context, thenmResId);
        this.mContext = context;
        this.listener = listener;
        this.inputType = inputType;
        this.budgetSingleInfo = budgetSingleInfo;
//        this.editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }


    /*public ShowChangePassword(AdapterView.OnItemClickListener onItemClickListener, int dialog, OnCloseListener listener) {
        super(context, thenmResId);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_password);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    public ShowChangePassword setTitle(String title) {
        this.title = title;
        return this;
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.et_password);
        if (inputType == 1) {
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        titleTxt = (TextView) findViewById(R.id.tv_title);
        submitTxt = (TextView) findViewById(R.id.bt_ok);
        cancelTxt = (TextView) findViewById(R.id.bt_cancel);
        submitTxt.setOnClickListener(this);
        cancelTxt.setOnClickListener(this);

        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);


        new Timer().schedule(new TimerTask() {
            @SuppressLint("WrongConstant")
            public void run() {
                ((InputMethodManager) editText.getContext().getSystemService("input_method")).showSoftInput(editText, 0);
            }
        }, 100L);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancel:
                if (listener != null) {
                    listener.onClick(this, false, editText.getText().toString());
                }
                this.dismiss();
                break;
            case R.id.bt_ok:
                if (listener != null) {
                    listener.onClick(this, true, editText.getText().toString());
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, String s);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.dismiss();
    }
}
