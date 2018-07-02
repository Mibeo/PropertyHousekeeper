package com.example.bingjiazheng.propertyhousekeeper.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer;
import com.example.bingjiazheng.propertyhousekeeper.Entity.BudgetSingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.get_budget_data;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.list_key;

/**
 * Created by bingjia.zheng on 2018/4/16.
 */

public class BudgetAdapter extends BaseAdapter {
    private List<BudgetSingleInfo> TypeData;
    private Context context;
    private String Table;
    private MySQLiteHelper helper;

    private SQLiteDatabase sqLiteDatabase;
//    private LayoutInflater inflater;//界面生成器

    public BudgetAdapter(Context context, String user, String Table,int Life_Stage, String budget_month) {
        TypeData = new ArrayList<>();
        helper = DbManger.getIntance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        this.context = context;
        this.Table = Table;
        if (!TypeData.isEmpty()) {
            TypeData.clear();
        }
        if(!list_key.isEmpty()){
            list_key.clear();
        }
        TypeData = get_budget_data(context, user, Life_Stage, budget_month);
        HashMap<String, Double> spend_db = DataServer.get_spend_Data(context, user, "spend_db", Life_Stage, budget_month);
        for(int i = 0;i <spend_db.size();i++){
            for(int j = 0;j<TypeData.size();j++){
                if(list_key.get(i).equals(TypeData.get(j).getType())){
                    sqLiteDatabase = helper.getWritableDatabase();
                    sqLiteDatabase.execSQL("update '" + "budget_db" + "'set user='" + user + "',life='" + Life_Stage + "',type='" + list_key.get(i)
                            +"',spended_money='"+spend_db.get(list_key.get(i))+ "',budget_month='" + budget_month + "' where " + "_id='" + TypeData.get(j).get_id() + "'");
                }
            }
        }
        if(!TypeData.isEmpty()){
            TypeData.clear();
        }
        TypeData = get_budget_data(context, user, Life_Stage, budget_month);
        sqLiteDatabase.close();
    }

    @Override
    public int getCount() {
        return TypeData.size();
    }

    @Override
    public Object getItem(int position) {
        return TypeData.get(position);
    }

    @Override
    public long getItemId(int position) {
//        return Long.parseLong(TypeData.get(position));
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.budget_listview, null);
//            convertView = inflater.inflate(R.layout.budget_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_type = convertView.findViewById(R.id.tv_type1);
            viewHolder.tv_budget_money = convertView.findViewById(R.id.tv_budget_money);
            viewHolder.tv_remaining_money = convertView.findViewById(R.id.tv_remaining_money);
            viewHolder.progressBar = convertView.findViewById(R.id.progressBar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (TypeData != null) {
            viewHolder.tv_type.setText(TypeData.get(position).getType());
            double budget_money = TypeData.get(position).getBudget_money();
            viewHolder.tv_budget_money.setText("预算 ￥"+ budget_money);
            double remaining_money = 0.0;
            if(budget_money!=0.0){
                remaining_money = budget_money - TypeData.get(position).getSpended_money();
            }

            viewHolder.tv_remaining_money.setText("余额 ￥"+remaining_money);
            if (remaining_money < 0) {
                viewHolder.tv_remaining_money.setTextColor(Color.parseColor("#FF0000"));
//                viewHolder.progressBar.setBackgroundColor(Color.parseColor("#FF0000"));
                viewHolder.progressBar.setDrawingCacheBackgroundColor(Color.parseColor("#FF0000"));
            } else {
                viewHolder.tv_remaining_money.setTextColor(Color.parseColor("#000000"));
            }
            double progress = 0;
            if(budget_money!=0.0){
                progress = remaining_money / budget_money *100;
            }
            viewHolder.progressBar.setProgress((int) progress);
            return convertView;
        } else {
            return null;
        }
    }

    static class ViewHolder {
        TextView tv_type;
        ProgressBar progressBar;
        TextView tv_budget_money;
        TextView tv_remaining_money;
    }
}
