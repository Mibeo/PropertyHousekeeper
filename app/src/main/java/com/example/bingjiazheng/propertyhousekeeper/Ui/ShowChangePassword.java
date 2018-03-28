package com.example.bingjiazheng.propertyhousekeeper.Ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class ShowChangePassword extends Dialog implements View.OnClickListener {
    private EditText editText;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String ip;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private int i;

    public ShowChangePassword(Context context,int thenmResId,OnCloseListener listener){
        super(context,thenmResId);
        this.mContext = context;
        this.listener = listener;
    }

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
//        InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        im.showSoftInput(getCurrentFocus(), 0);
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
