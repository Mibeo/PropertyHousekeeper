package com.example.bingjiazheng.propertyhousekeeper.Activity;

import com.example.bingjiazheng.propertyhousekeeper.ContentActivity.NoteContentActivity;

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
