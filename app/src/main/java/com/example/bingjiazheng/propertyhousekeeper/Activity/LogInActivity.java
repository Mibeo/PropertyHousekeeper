package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.ImageViewPlus;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/3/21.
 */

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_login, bt_register;
    private ImageViewPlus imageViewPlus;
    private ImageView iv_eye;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor editor;
    private EditText et_user, et_password;
    private CheckBox checkBox;
    private RelativeLayout rl,rl_visible;
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private String sql = "create table if not exists password_db(user varchar(20),new_password varchar(20),old_password varchar(20))";
    private boolean isHide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.left_to_right, R.anim.hold);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        et_password = (EditText) findViewById(R.id.et_password);
        imageViewPlus = findViewById(R.id.image_head);
        imageViewPlus.setImageResource(R.mipmap.head);
        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setOnClickListener(this);
        iv_eye = findViewById(R.id.iv_eye);
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mSettings.edit();
        if(mSettings.getBoolean("visible",false)){
            iv_eye.setImageResource(R.mipmap.visible);
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            iv_eye.setImageResource(R.mipmap.invisible);
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        et_user = (EditText) findViewById(R.id.et_user);

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
                et_password.setText(getUserOldPassword(et_user.getText().toString()));
            }
        }
        rl_visible = findViewById(R.id.rl_visible);
        rl_visible.setOnClickListener(this);
    }

    private boolean userIsexists(String s) {
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("password_db", new String[]{"user"}, "user like ?", new String[]{"" + s}, null, null, null);
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

    private String getUserOldPassword(String s) {
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("password_db", new String[]{"user,old_password"}, "user like ?", new String[]{s}, null, null, null);
        String string = null;
        while (cursor.moveToNext()) {
            string = cursor.getString(1);
        }
        cursor.close();
        sqLiteDatabase.close();
        return string;
    }
    private String getUserNewPassword(String s) {
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("password_db", new String[]{"user,new_password"}, "user like ?", new String[]{"" + s}, null, null, null);
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
        contentValues.put("new_password", "" + s2);
        contentValues.put("old_password", "" + s2);
        sqLiteDatabase.insert("password_db", null, contentValues);
        sqLiteDatabase.close();
        showText(this, "注册成功");
    }
    private void updateUserOldPassword(String user,String password) {
        sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("old_password", password);
        sqLiteDatabase.update("password_db", contentValues,"user="+user,null);
        sqLiteDatabase.close();
    }
    /*private void updatePassword(String password){
        sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("new_password",password);
        sqLiteDatabase.update("password_db",contentValues,"user="+user,null);
        sqLiteDatabase.close();
    }*/
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
            case R.id.rl_visible:
                hidePassword();
                break;
        }
    }

    private void hidePassword() {
        if(mSettings.getBoolean("visible",false)){
            editor.putBoolean("visible",false);
            iv_eye.setImageResource(R.mipmap.invisible);
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else {
            editor.putBoolean("visible",true);
            iv_eye.setImageResource(R.mipmap.visible);
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        editor.apply();
    }

    private void login() {
        String user = et_user.getText().toString();
        String password = et_password.getText().toString();
        if (!"".equals(user) && !"".equals(password)) {
            if (userIsexists(user)) {
                if (password.equals(getUserNewPassword(user))) {
                    updateUserOldPassword(user,password);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    LogInActivity.this.finish();
                } else {
                    showText(this, "密码不正确，请重新输入");
                }
            } else {
                showText(this, "用户名不存在，请注册！");
            }
        } else if (!"".equals(user) && "".equals(password)) {
            showText(this, "请输入密码");
        } else {
            showText(this, "请输入用户名和密码!");
        }
    }

    private void register() {
        String user = et_user.getText().toString();
        String password = et_password.getText().toString();
        if (!"".equals(user) && !"".equals(password)) {
            if (userIsexists(user)) {
                showText(this, "该用户已存在，请登录");
            } else {
                setUserPassword(user, password);
            }
        } else if (!"".equals(user) && "".equals(password)) {
            showText(this, "请输入密码");
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


    @Override
    protected void onDestroy() {
        editor.putString("TheLastUser", et_user.getText().toString());
        editor.commit();
        super.onDestroy();
    }
}
