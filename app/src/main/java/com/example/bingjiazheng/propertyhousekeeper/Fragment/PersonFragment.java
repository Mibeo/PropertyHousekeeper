package com.example.bingjiazheng.propertyhousekeeper.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Activity.LogInActivity;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.ShowChangePassword;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;


/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

@SuppressLint("ValidFragment")
public class PersonFragment extends Fragment implements View.OnClickListener {
    private String user;
    private TextView tv_user;
    private RelativeLayout rl_change, rl_exit;
    private SQLiteDatabase sqLiteDatabase;
    private MySQLiteHelper helper;

    @SuppressLint("ValidFragment")
    public PersonFragment(String user) {
        this.user = user;
    }

    public static PersonFragment newInstance(String s, String user) {
        PersonFragment homeFragment = new PersonFragment(user);
        Bundle bundle = new Bundle();
        bundle.putString("args", s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_person, container, false);
        tv_user = view.findViewById(R.id.tv_user);
        tv_user.setText(user);
        rl_change = view.findViewById(R.id.rl_change);
        rl_change.setOnClickListener(this);
        rl_exit = view.findViewById(R.id.rl_exit);
        rl_exit.setOnClickListener(this);
//        Bundle bundle = getArguments();
//        String s = bundle.getString("args");
//        TextView textView = (TextView) view.findViewById(R.id.fragment_text_view);
//        textView.setText(s);
        helper = DbManger.getIntance(getContext());
        return view;
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
        contentValues.put("password", "" + s2);
        sqLiteDatabase.insert("password_db", null, contentValues);
        sqLiteDatabase.close();
        showText(getContext(), "注册成功");
    }
    private void updatePassword(String password){
        sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("new_password",password);
        sqLiteDatabase.update("password_db",contentValues,"user="+user,null);
        sqLiteDatabase.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_change:
                chang_password();
                break;
            case R.id.rl_exit:
                Intent intent = new Intent(getContext(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
    private void chang_password(){
        new ShowChangePassword(getActivity(), R.style.dialog, new ShowChangePassword.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, final String editText) {
                if (confirm) {
                    if (editText.equals(getUserNewPassword(user))) {
                        new ShowChangePassword(getActivity(), R.style.dialog, new ShowChangePassword.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm, final String editText) {
                                if (confirm) {
                                    if(editText.equals(getUserNewPassword(user))){
                                        showText(getContext(),"该密码已存在，请输入其他密码");
                                    }else{
                                        updatePassword(editText);
                                        showText(getContext(),"修改成功");
                                        dialog.dismiss();
                                    }
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        }).setTitle("请输入密码").show();
                        dialog.dismiss();
                    } else {
                        showText(getContext(),"密码错误，请重新输入");
                    }
                } else {
                    dialog.dismiss();
                }
            }
        }).setTitle("请输入原始密码").show();
    }

}
