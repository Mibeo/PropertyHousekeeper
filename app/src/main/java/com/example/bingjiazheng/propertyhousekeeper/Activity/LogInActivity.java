package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.ImageViewPlus;
import com.example.bingjiazheng.propertyhousekeeper.Utils.SpendManger;
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
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private String sql = "create table if not exists password_db(user varchar(20),password varchar(20))";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.hold);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        helper = SpendManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
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
                editor.apply();
            }
        });
        if (!"".equals(mSettings.getString("TheLastUser", ""))) {
            et_user.setText(mSettings.getString("TheLastUser", ""));
            if (checkBox.isChecked()) {
                et_password.setText(getuserPassword(et_user.getText().toString()));
            }
        }

    }

    private boolean userIsexists(String s) {
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("password_db", new String[]{"user,password"}, "user like ?", new String[]{"" + s}, null, null, null);
        if (!cursor.moveToNext()) {
            cursor.close();
            sqLiteDatabase.close();
            return false;
        } else {
            cursor.close();
            sqLiteDatabase.close();
            return true;
        }
    }

    private String getuserPassword(String s) {
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("password_db", new String[]{"user,password"}, "user like ?", new String[]{"" + s}, null, null, null);
        String string = null;
        while (cursor.moveToNext()) {
            string = cursor.getString(1);
        }
        cursor.close();
        sqLiteDatabase.close();
        return string;
    }

    private void setUserPassword(String s1, String s2) {
        sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", "" + s1);
        contentValues.put("password", "" + s2);
        sqLiteDatabase.insert("password_db", null, contentValues);
        sqLiteDatabase.close();
        showText(this,"注册成功");
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
        String user = et_user.getText().toString();
        String password = et_password.getText().toString();
        if (!"".equals(user) && !"".equals(password)) {
            if (userIsexists(user)) {
                if(password.equals(getuserPassword(user))){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
//                    LogInActivity.this.finish();
                }else {
                    showText(this,"密码不正确，请重新输入");
                }
            } else {
                showText(this, "用户名不存在，请注册！");
            }
        } else {
            if ("".equals(user)) {
                showText(this, "请输入用户名");
            } else if (!"".equals(user) && "".equals(password)) {
                showText(this, "请输入密码");
            }
        }
    }

    private void register() {
        String user = et_user.getText().toString();
        String password = et_password.getText().toString();
        if (!"".equals(user) && !"".equals(password)) {
            if(userIsexists(user)){
                showText(this,"该用户已存在，请输入密码登录");
            }else{
                setUserPassword(user,password);
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
