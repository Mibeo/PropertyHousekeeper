package com.example.bingjiazheng.propertyhousekeeper.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bingjiazheng.propertyhousekeeper.Entity.BudgetSingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;

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
    public static String string;
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

    public static List<SingleInfo> getData3(Context context, String Table, String user, int Life_Stage, String date) {
        data = new ArrayList<SingleInfo>();
        helper = DbManger.getIntance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = null;
//        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name = 'table' ", null);
        cursor = sqLiteDatabase.rawQuery("select count(*)  from sqlite_master where type ='table' and name = '" + Table + "'", null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
//                result = true;
                cursor = sqLiteDatabase.query(Table, null, "user like ? and life like ? and date like ? ", new String[]{user, Life_Stage + "", "%" + date + "%"}, null, null, null);
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
        if (!plan_data .isEmpty()) {
            plan_data.clear();
        }
        if (!HashData .isEmpty()) {
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
            int count = cursor.getInt(0);
            if (count > 0) {
                cursor = sqLiteDatabase.query("budget_db", null, "user like ? and life like ? and budget_month like ? ", new String[]{user, Life_Stage + "", budget_month}, null, null, null);
                if (cursor == null) {
                    return null;
                }
                if (!cursor.moveToNext()) {
                    addbudegetdata(context,user, Life_Stage, 0, 0, 0, budget_month);
//                    cursor = sqLiteDatabase.query("budget_db", null, "user like ? and life like ? and budget_month like ? ", new String[]{user, Life_Stage + "", budget_month}, null, null, null);
                }
                sqLiteDatabase = helper.getWritableDatabase();
                cursor = sqLiteDatabase.query("budget_db", null, "user like ? and life like ? and budget_month like ? ", new String[]{user, Life_Stage + "", budget_month}, null, null, null);
                while (cursor.moveToNext()) {
                    BudgetSingleInfo budgetSingleInfo = new BudgetSingleInfo();
                    budgetSingleInfo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                    budgetSingleInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                    budgetSingleInfo.setBudget_money(cursor.getDouble(cursor.getColumnIndex("budget_money")));
                    budgetSingleInfo.setSpended_money(cursor.getDouble(cursor.getColumnIndex("spended_money")));
//                    budgetSingleInfo.setRemaining_money(cursor.getDouble(cursor.getColumnIndex("remaining_money")));
                    budgetSingleInfos.add(budgetSingleInfo);
                }
                cursor.close();
                sqLiteDatabase.close();
                return budgetSingleInfos;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void addbudegetdata(Context context,String user, int life, double budget_money, double spended_money,
                                      double remaining_money, String budget_month) {
        sqLiteDatabase = helper.getWritableDatabase();
        List<String> typedata = new ArrayList<>();
        getData1(life, typedata);
        if(!HashData.isEmpty()){
            HashData.clear();
        }
        if(!list_key.isEmpty()){
            list_key.clear();
        }
        Table = "spend_db";
        HashData = DataServer.get_spend_Data(context,user,Table,life,budget_month);
        double d =0.0;
        //HashData.get(list_key.get(position))
        for (int i = 0; i < typedata.size(); i++) {
            sqLiteDatabase = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("user", user);
            contentValues.put("life", life);
            contentValues.put("type", typedata.get(i));
            contentValues.put("budget_money", budget_money);
//            Log.e("list_key",list_key.get(i));
            if(HashData.containsKey(typedata.get(i))){
                for (int j = 0; j<HashData.size();j++){
                    if(list_key.get(j).equals(typedata.get(i))){
                        contentValues.put("spended_money", HashData.get(list_key.get(j)));
                    }
                }
            }else {
                contentValues.put("spended_money", 0.0);
            }
//            contentValues.put("remaining_money", remaining_money);
            contentValues.put("budget_month", budget_month);
            sqLiteDatabase.insert("budget_db", null, contentValues);
            sqLiteDatabase.close();
        }
    }
    private void update_spend_remaining_data(Context context,int Life_Stage, String selector_date) {
        total_money = 0.0;
        Table = "spend_db";
        if (data != null) {
            data.clear();
        }
        if (HashData != null) {
            HashData.clear();
        }
        data = getData3(context, Table, user, Life_Stage, selector_date);
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (HashData.containsKey(data.get(i).getType())) {
                    total_money += data.get(i).getMoney();
                    Double aDouble = HashData.get(data.get(i).getType());
                    aDouble = aDouble + data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), aDouble);
                } else {
                    total_money += data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), data.get(i).getMoney());
                }
            }
            if (!HashData.isEmpty()) {
//                addbudegetdata(user, Life_Stage, 0.0, 0.0, 0.0, selector_date);
                Iterator iterator = HashData.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
//                    update_spend_data(user, Life_Stage, String.valueOf(entry.getKey()), budget_money, total_money, 0.0, selector_date);
//                    update_spended_data(user, Life_Stage, (String) entry.getKey(), (Double) entry.getValue(), selector_date);
                }
            }
        }
    }
    public  static String getData(int life_stage,int type){
        switch (life_stage){
            case 1:
                switch (type){
                    case 1:
                        string = "储蓄:100% ; 股票:0% ; 债券:0% ; 基金:0% ; 寿险:0% ";
                        break;
                    case 2:
                        string = "储蓄:75% ; 股票:0% ; 债券:15% ; 基金:10% ; 寿险:0% ";
                        break;
                    case 3:
                        string = "储蓄:60% ; 股票:15% ; 债券:15% ; 基金:10% ; 寿险:0% ";
                        break;
                }
                break;
            case 2:
                switch (type){
                    case 1:
                        string = "储蓄:50% ; 股票:5% ; 债券:26% ; 基金:13% ; 寿险:6% ";
                    break;
                    case 2:
                        string = "储蓄:30% ; 股票:30% ; 债券:19% ; 基金:15% ; 寿险:6% ";
                    break;
                    case 3:
                        string = "储蓄:15% ; 股票:70% ; 债券:5% ; 基金:5% ; 寿险:5% ";
                    break;
                }
                break;
            case 3:
                switch (type){
                    case 1:
                        string = "储蓄:48% ; 股票:5% ; 债券:20% ; 基金:19% ; 寿险:6% ";
                    break;
                    case 2:
                        string = "储蓄:30% ; 股票:28% ; 债券:12% ; 基金:24% ; 寿险:6% ";
                    break;
                    case 3:
                        string = "储蓄:10% ; 股票:70% ; 债券:6% ; 基金:8% ; 寿险:6% ";
                    break;
                }
                break;
            case 4:
                switch (type){
                    case 1:
                        string = "储蓄:58% ; 股票:0% ; 债券:20% ; 基金:10% ; 寿险:12% ";
                    break;
                    case 2:
                        string = "储蓄:35% ; 股票:22% ; 债券:15% ; 基金:18% ; 寿险:10% ";
                    break;
                    case 3:
                        string = "储蓄:18% ; 股票:62% ; 债券:6% ; 基金:5% ; 寿险:9% ";
                    break;
                }
                break;
        }
        return string;
    }
    public static HashMap<String,Double> PercentData = new HashMap<>();
    public static HashMap<String,Double> getPercentData(int life){
        switch (life){
            case 1:
                if(!PercentData.isEmpty()){
                    PercentData.clear();
                }
                PercentData.put("食品烟酒",41.0);
                PercentData.put("衣服饰品",13.0);
                PercentData.put("生活用品",7.0);
                PercentData.put("行车交通",4.0);
                PercentData.put("交流通讯",5.0);
                PercentData.put("休闲娱乐",7.0);
                PercentData.put("学习进修",5.0);
                PercentData.put("医疗保健",4.0);
                PercentData.put("恋爱开销",11.0);
                PercentData.put("其他",3.0);
                break;
            case 2:
                if(!PercentData.isEmpty()){
                    PercentData.clear();
                }
                PercentData.put("食品烟酒",22.0);
                PercentData.put("居家物业",11.0);
                PercentData.put("衣服饰品",11.0);
                PercentData.put("生活用品",5.0);
                PercentData.put("行车交通",5.0);
                PercentData.put("交流通讯",3.0);
                PercentData.put("人情往来",11.0);
                PercentData.put("休闲娱乐",7.0);
                PercentData.put("学习进修",5.0);
                PercentData.put("医疗保健",3.0);
                PercentData.put("恋爱开销",11.0);
                PercentData.put("其他",6.0);
                break;
            case 3:
                if(!PercentData.isEmpty()){
                    PercentData.clear();
                }
                PercentData.put("食品烟酒",18.0);
                PercentData.put("居家物业",10.0);
                PercentData.put("父母费用",13.0);
                PercentData.put("儿女费用",15.0);
                PercentData.put("衣服饰品",7.0);
                PercentData.put("生活用品",5.0);
                PercentData.put("行车交通",6.0);
                PercentData.put("交流通讯",3.0);
                PercentData.put("人情往来",8.0);
                PercentData.put("休闲娱乐",5.0);
                PercentData.put("学习进修",4.0);
                PercentData.put("医疗保健",4.0);
                PercentData.put("其他",2.0);
                break;
            case 4:
                if(!PercentData.isEmpty()){
                    PercentData.clear();
                }
                PercentData.put("食品烟酒",26.0);
                PercentData.put("居家物业",6.0);
                PercentData.put("儿女费用",3.0);
                PercentData.put("衣服饰品",7.0);
                PercentData.put("生活用品",6.0);
                PercentData.put("行车交通",6.0);
                PercentData.put("人情往来",9.0);
                PercentData.put("交流通讯",5.0);
                PercentData.put("休闲娱乐",9.0);
                PercentData.put("学习进修",4.0);
                PercentData.put("医疗保健",17.0);
                PercentData.put("其他",2.0);
                break;
        }
        return PercentData;
    }
}
