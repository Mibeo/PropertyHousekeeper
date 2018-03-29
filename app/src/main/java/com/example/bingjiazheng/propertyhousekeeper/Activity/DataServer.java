package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class DataServer {
    private static MySQLiteHelper helper;
    private static SQLiteDatabase sqLiteDatabase;
    private static String table;
    private static String user;
    private static int id = 0;
    private static List<SingleInfo> data;
//    private static List<String> data = new ArrayList<String>();

    /*public static List<String> getData(int offset, int maxnumber) {
        for (int i = 0; i < maxnumber; i++) {
            data.add("" + i);
        }
        return data;
    }*/
//    public DataServer(ListviewAdapter context, String table, String user) {
//
//        this.table = table;
//        this.user = user;
//    }

   /* *
     * 判断某张表是否存在
     *
     * @param tabName 表名
     * @return*/
    /*public boolean tabIsExist(String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();//此this是继承SQLiteOpenHelper类得到的
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='spend_db' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }*/

    public static List<SingleInfo> getData(Context context, String table, String user) {
        data = new ArrayList<SingleInfo>();
        int id = 0;
        helper = DbManger.getIntance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = null;
        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name ='spend_db' ", null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
//                result = true;
                cursor = sqLiteDatabase.query(table, null, "user like ?", new String[]{user}, null, null, null);
                if (cursor == null) {
                    return null;
                } else {
                    while (cursor.moveToNext()) {
                        id++;
                        SingleInfo singleInfo = new SingleInfo();
                        singleInfo.setId(id);
                        singleInfo.setLife(cursor.getInt(cursor.getColumnIndex("life")));
                        singleInfo.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                        singleInfo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                        singleInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                        singleInfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        singleInfo.setPayer_payee(cursor.getString(cursor.getColumnIndex("payer_payee")));
                        singleInfo.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
                        data.add(singleInfo);
                        Log.e("data", data.get(0).getAddress() + "");
                    }
                }
                cursor.close();
                sqLiteDatabase.close();
                return data;
            }else{
                return null;
            }
        }else{
            return null;
        }


    }

}
