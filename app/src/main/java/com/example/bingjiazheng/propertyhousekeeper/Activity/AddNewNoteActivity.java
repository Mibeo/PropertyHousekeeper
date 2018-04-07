package com.example.bingjiazheng.propertyhousekeeper.Activity;

import com.example.bingjiazheng.propertyhousekeeper.ContentActivity.NoteContentActivity;

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
