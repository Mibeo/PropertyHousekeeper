package com.example.bingjiazheng.propertyhousekeeper.Utils;

import android.content.Context;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class DbManger {
    private static MySQLiteHelper helper;
    public static MySQLiteHelper getIntance(Context context){
        if(helper == null){
            helper = new MySQLiteHelper(context,"database.db");
        }
        return helper;
    }
}
