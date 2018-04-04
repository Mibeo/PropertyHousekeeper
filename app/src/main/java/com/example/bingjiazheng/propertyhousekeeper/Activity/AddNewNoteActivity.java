package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.ContentValues;
import android.content.Intent;
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

public class AddNewNoteActivity extends NoteContentActivity {

    @Override
    protected void init() {
        tv_Title.setText("新增便签");
        isModify=false;
    }

}
