package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.BudgetSingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

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

    public static List<SingleInfo> getData(Context context, String table, String user, int Life_Stage) {
        data = new ArrayList<SingleInfo>();
        int id = 0;
        helper = DbManger.getIntance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = null;
//        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name ='spend_db' ", null);
//        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name ='spend_db' ", null);
//        if (cursor.moveToNext()) {
//            int count = cursor.getInt(0);
//            if (count > 0) {
////                result = true;
        cursor = sqLiteDatabase.query(table, null, "user like ? and life like ?", new String[]{user, Life_Stage + ""}, null, null, null);
        if (cursor == null) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                id++;
                SingleInfo singleInfo = new SingleInfo();
                singleInfo.setId(id);
                singleInfo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                singleInfo.setLife(cursor.getInt(cursor.getColumnIndex("life")));
                singleInfo.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                singleInfo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                singleInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                singleInfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                singleInfo.setPayer_payee(cursor.getString(cursor.getColumnIndex("payer_payee")));
                singleInfo.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
                data.add(singleInfo);
//                        Log.e("data", data.get(0).getAddress() + "");
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return data;
//            }else{
//                return null;
//            }
//        }else{
//            return null;
//        }
    }

    public static List<SingleInfo> getData2(Context context, String table, String user, int Life_Stage) {
        data = new ArrayList<SingleInfo>();
        int id = 0;
        helper = DbManger.getIntance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = null;
//        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name ='flag_db' ", null);
//        if (cursor.moveToNext()) {
//            int count = cursor.getInt(0);
//            if (count > 0) {
//                result = true;
        cursor = sqLiteDatabase.query(table, null, "user like ? and life like ?", new String[]{user, Life_Stage + ""}, null, null, null);
        if (cursor == null) {
            return null;
        } else {
            while (cursor.moveToNext()) {
                id++;
                SingleInfo singleInfo = new SingleInfo();
                singleInfo.setId(id);
                singleInfo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                singleInfo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                singleInfo.setText(cursor.getString(cursor.getColumnIndex("text")));
                data.add(singleInfo);
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return data;
//            }else{
//                return null;
//            }
//        }else{
//            return null;
//        }
    }

    public static List<SingleInfo> getData3(Context context, String table, String user, int Life_Stage, String date) {
        data = new ArrayList<SingleInfo>();
        helper = DbManger.getIntance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = null;
//        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name = 'table' ", null);
        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name = '" + table + "';", null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
//                result = true;
                cursor = sqLiteDatabase.query(table, null, "user like ? and life like ? and date like ? ", new String[]{user, Life_Stage + "", "%" + date + "%"}, null, null, null);
                if (cursor == null) {
                    return null;
                } else {
                    while (cursor.moveToNext()) {
                        SingleInfo singleInfo = new SingleInfo();
//                        singleInfo.setId(id);
//                        singleInfo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                        singleInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                        singleInfo.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                        data.add(singleInfo);
                    }
                }
                cursor.close();
                sqLiteDatabase.close();
                Log.e("data", data.toString() + "");
                return data;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static List<String> getDataSource() {
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("学生");
        spinnerList.add("工作未婚");
        spinnerList.add("工作已婚");
        spinnerList.add("退休");
        return spinnerList;
    }

    public static double total_money;
    public static String Table;
    public static HashMap<String, Double> HashData = new HashMap<>();
    public static List<String> list_key = new ArrayList<>();

    //    public static List<String> list_key=new ArrayList<>();
//    public static List<String> list_key = new ArrayList<>();//定义一个用来存放key列表
    public static List<SingleInfo> plan_data = new ArrayList<>();

    public static HashMap<String, Double> get_spend_Data(Context context, String user, String Table, int Life_Stage, String selector_date) {
        total_money = 0.0;
        if (plan_data != null) {
            plan_data.clear();
        }
        if (HashData != null) {
            HashData.clear();
        }
        plan_data = getData3(context, Table, user, Life_Stage, selector_date);
        if (plan_data != null) {
            for (int i = 0; i < plan_data.size(); i++) {
                if (HashData.containsKey(plan_data.get(i).getType())) {
                    total_money += plan_data.get(i).getMoney();
                    Double aDouble = HashData.get(plan_data.get(i).getType());
                    aDouble = aDouble + plan_data.get(i).getMoney();
                    HashData.put(plan_data.get(i).getType(), aDouble);
                } else {
                    total_money += plan_data.get(i).getMoney();
                    HashData.put(plan_data.get(i).getType(), plan_data.get(i).getMoney());
                }
            }
            Iterator<String> iter = HashData.keySet().iterator();
            if (list_key != null) {
                list_key.clear();
            }
            while (iter.hasNext()) {
                list_key.add(iter.next());
            }
            return HashData;
        } else {
            return null;
        }

        /*Iterator iterator = HashData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
//            entries.add(new PieEntry((Float.valueOf(entry.getValue()+"") / Float.valueOf(total_money+"") * 100), entry.getKey()+""));
        }*/
    }

    public static BudgetSingleInfo budgetSingleInfo;
    public static List<BudgetSingleInfo> budgetSingleInfos = new ArrayList<>();

    public static List<BudgetSingleInfo> get_budget_data(Context context, String user, int Life_Stage, String budget_month) {
        if (!budgetSingleInfos.isEmpty()) {
            budgetSingleInfos.clear();
        }
        helper = DbManger.getIntance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = null;
        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name ='budget_db' ", null);
        if (cursor.moveToNext()) {
            Log.e("cursor", 1 + "");
            int count = cursor.getInt(0);
            Log.e("count", count + "");
            if (count > 0) {
                Log.e("cursor", 2 + "");
                cursor = sqLiteDatabase.query("budget_db", null, "user like ? and life like ? and budget_month like ? ", new String[]{user, Life_Stage + "", budget_month}, null, null, null);
                Log.e("cursor", cursor.toString());
                if (cursor == null) {
                    return null;
                }
                if (!cursor.moveToNext()) {
                    addbudegetdata(user, Life_Stage, 0, 0, 0, budget_month);
                    cursor = sqLiteDatabase.query("budget_db", null, "user like ? and life like ? and budget_month like ? ", new String[]{user, Life_Stage + "", budget_month}, null, null, null);
                }
                Log.e("cur", cursor.moveToNext() + "");
                while (cursor.moveToNext()) {
                    Log.e("cursor", 4 + "");
                    BudgetSingleInfo budgetSingleInfo = new BudgetSingleInfo();
                    budgetSingleInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                    budgetSingleInfo.setBudget_money(cursor.getDouble(cursor.getColumnIndex("budget_money")));
                    budgetSingleInfo.setSpended_money(cursor.getDouble(cursor.getColumnIndex("spended_money")));
                    budgetSingleInfo.setRemaining_money(cursor.getDouble(cursor.getColumnIndex("remaining_money")));
                    budgetSingleInfos.add(budgetSingleInfo);
                }
                cursor.close();
                sqLiteDatabase.close();
                Log.e("cursor", 5 + "");
                return budgetSingleInfos;

            } else {
                Log.e("cursor", 6 + "");
                return null;
            }
        } else {
            Log.e("cursor", 7 + "");
            return null;
        }
    }

    public static void addbudegetdata(String user, int life, double budget_money, double spended_money,
                                      double remaining_money, String budget_month) {
        sqLiteDatabase = helper.getWritableDatabase();
        List<String> typedata = new ArrayList<>();
        getData1(life, typedata);
        Log.e("typedata", typedata.toString());
        for (int i = 0; i < typedata.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("user", user);
            contentValues.put("life", life);
            contentValues.put("type", typedata.get(i));
            contentValues.put("budget_money", budget_money);
            contentValues.put("spended_money", spended_money);
            contentValues.put("remaining_money", remaining_money);
            contentValues.put("budget_month", budget_month);
            sqLiteDatabase.insert("budget_db", null, contentValues);
        }
    }
}
