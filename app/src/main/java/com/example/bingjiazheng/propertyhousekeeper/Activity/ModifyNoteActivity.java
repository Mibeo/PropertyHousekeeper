package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public class ModifyNoteActivity extends NoteContentActivity {

    @Override
    protected void init() {
        tv_Title.setText("修改便签");
        isModify=true;
    }

}
