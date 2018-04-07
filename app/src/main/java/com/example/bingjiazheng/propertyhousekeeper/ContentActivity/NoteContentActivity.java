package com.example.bingjiazheng.propertyhousekeeper.ContentActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.Calendar;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public abstract class NoteContentActivity extends AppCompatActivity {
    private RelativeLayout iv_back;
    protected TextView tv_Title;
    private Spinner spinner;
    private String user;
    protected EditText et_text;
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private ImageView iv_save;
    private String Oldtext = "";
    protected boolean isModify;
    protected String old_text;
    protected String old_date;
    protected int old__id;
    private SingleInfo singleInfo;
    protected int Life_Stage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        initview();
    }

    private void initview() {
        user = getIntent().getStringExtra("user");
        Life_Stage = getIntent().getIntExtra("Life_Stage",0);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_Title = findViewById(R.id.tv_Title);
//        tv_Title.setText("新增便签");
        init();
        et_text = findViewById(R.id.et_text);
        if(isModify){
            singleInfo = getIntent().getParcelableExtra("singleInfo");
            old__id = singleInfo.get_id();
            old_text = singleInfo.getText();
            old_date = singleInfo.getDate();
            et_text.setText(singleInfo.getText());
        }
        iv_save = findViewById(R.id.iv_save);
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isModify){
                    updateNote();
                }else {
                    addNote();
                }
            }
        });
    }

    private void updateNote() {
        String date = getDate();
        String text = et_text.getText().toString();
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL("update flag_db set _id='"+old__id+"',user='"+user+"',life='"+Life_Stage+"',date='"+date+"',text='"+text+
                "' where _id='"+old__id+"'");
        /*sqLiteDatabase.execSQL("update flag_db set user='"+user+"',life='"+Life_Stage+"',date='"+date+"',text='"+text+
                "' where user='"+user+"' and date='"+old_date+"' and text='"+old_text+"'");*/
        /*sqLiteDatabase.execSQL("update '"+table+"'set user='"+user+"',life='"+life+"',date='"+date+"',type='"+type+"',address='"+address
                +"',payer_payee='"+payer_payee+"',remark='"+remark+"' where "+"user='"+user+"' and life='"+life+"' and money='"+old_money+
                "' and date='"+old_date+"' and type='"+old_type+"' and address='"+old_address+"' and payer_payee='"+old_payer_payee+
                "' and remark='"+old_remark+"'");*/
        showText(this,"修改成功");
        sqLiteDatabase.close();
        Intent intent = new Intent();
        setResult(2,intent);
    }

    protected abstract void init();

    private void addNote() {
        String text = et_text.getText().toString();
        if (!text.equals("") && !text.equals(Oldtext)) {
            Oldtext = text;
            helper = DbManger.getIntance(getApplicationContext());
            sqLiteDatabase = helper.getWritableDatabase();
            String date = getDate();
            ContentValues contentValues = new ContentValues();
            contentValues.put("user", user);
            contentValues.put("life",Life_Stage);
            contentValues.put("date", date);
            contentValues.put("text", text);
            sqLiteDatabase.insert("flag_db", null, contentValues);
            sqLiteDatabase.close();
            showText(getApplicationContext(), "保存成功");
            Intent intent = new Intent();
            setResult(2, intent);
        } else if (!text.equals("") && text.equals(Oldtext)) {
            showText(getApplicationContext(), "已保存");
        }
    }
    protected String getDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + month + "-" + day;
        return date;
    }

}
