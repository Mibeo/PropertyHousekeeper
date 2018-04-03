package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/3/29.
 */

public class AddNewNoteActivity extends AppCompatActivity {
    private RelativeLayout iv_back;
    private TextView tv_Title;
    private Spinner spinner;
    private String user;
    private EditText et_text;
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private ImageView iv_save;
    private String Oldtext="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        initview();
    }

    private void initview() {

        user = getIntent().getStringExtra("user");
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("新增便签");
        et_text = findViewById(R.id.et_text);
        iv_save = findViewById(R.id.iv_save);
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }

    private void addNote() {
        String text = et_text.getText().toString();
        if (!text.equals("") && !text.equals(Oldtext)) {
            Oldtext = text;
            helper = DbManger.getIntance(getApplicationContext());
            sqLiteDatabase = helper.getWritableDatabase();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String date = year + "-" + month + "-" + day;
            ContentValues contentValues = new ContentValues();
            contentValues.put("user", user);
            contentValues.put("date", date);
            contentValues.put("text", text);
            sqLiteDatabase.insert("flag_db", null, contentValues);
            sqLiteDatabase.close();
            showText(getApplicationContext(), "保存成功");
        }else if(!text.equals("") && text.equals(Oldtext)){
            showText(getApplicationContext(),"已保存");
        }
    }
}
