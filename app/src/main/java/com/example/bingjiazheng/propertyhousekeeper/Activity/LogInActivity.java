package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.ImageViewPlus;
import com.example.bingjiazheng.propertyhousekeeper.Utils.utils;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.utils.*;

/**
 * Created by bingjia.zheng on 2018/3/21.
 */

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_login, bt_register;
    private ImageViewPlus imageViewPlus;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor editor;
    private EditText et_user, et_password;
    private CheckBox checkBox;
    private RelativeLayout rl;
    private TextView tv_pass;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.hold);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        imageViewPlus = findViewById(R.id.image_head);
        imageViewPlus.setImageResource(R.mipmap.head);
        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setOnClickListener(this);
        tv_pass = (TextView) findViewById(R.id.tv_pass);
        tv_pass.setOnClickListener(this);
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mSettings.edit();
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);

        checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setChecked(mSettings.getBoolean("RememberPassword", false));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    editor.putBoolean("RememberPassword", true);
                } else {
                    editor.putBoolean("RememberPassword", false);
                }
                editor.commit();
            }
        });
        if (!"".equals(mSettings.getString("TheLastUser", ""))) {
            et_user.setText(mSettings.getString("TheLastUser", ""));
            if (checkBox.isChecked()) {
                et_password.setText(mSettings.getString("" + et_user.getText(), ""));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_register:
                register();
                break;
            case R.id.rl:
                hideSoftInput();//隐藏输入法
                break;
            case R.id.tv_pass:
                pass();
                break;
        }
    }

    private void login() {
        if (!"".equals(et_user.getText().toString()) && !"".equals(et_password.getText().toString())) {
            if (et_password.getText().toString().equals(mSettings.getString(et_user.getText() + "", ""))) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                LogInActivity.this.finish();
            } else {
                showText(this, "请输入正确密码");
            }
        } else {
            if ("".equals(et_user.getText().toString())) {
                showText(this, "请输入用户名");
            } else if (!"".equals(et_user.getText().toString()) && "".equals(et_password.getText().toString())) {
                showText(this, "请输入密码");
            }
        }
    }

    private void register() {
        if (!"".equals(et_user.getText().toString()) && !"".equals(et_password.getText().toString())) {
            if (!"".equals(mSettings.getString("" + et_user.getText(), ""))) {
                showText(this, "该用户名已存在，请输入密码登录");
            } else {
                editor.putString("" + et_user.getText().toString(), et_password.getText().toString());
                editor.commit();
                showText(this, "注册成功");
            }
        } else {
            showText(this, "请输入用户名和密码!");
        }
    }

    private void hideSoftInput() {
        assert ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)) != null;
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(this.getCurrentFocus().
                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void pass() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        editor.putString("TheLastUser", et_user.getText().toString());
        editor.commit();
        super.onDestroy();
    }
}
