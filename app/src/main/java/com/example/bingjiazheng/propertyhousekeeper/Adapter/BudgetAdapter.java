package com.example.bingjiazheng.propertyhousekeeper.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bingjiazheng.propertyhousekeeper.Entity.BudgetSingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger;

import java.util.ArrayList;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Activity.DataServer.getData;
import static com.example.bingjiazheng.propertyhousekeeper.Activity.DataServer.get_budget_data;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;

/**
 * Created by bingjia.zheng on 2018/4/16.
 */

public class BudgetAdapter extends BaseAdapter {
    private List<BudgetSingleInfo> TypeData;
    private Context context;
//    private LayoutInflater inflater;//界面生成器

    public BudgetAdapter(Context context, String user, int Life_Stage, String budget_month) {
        TypeData = new ArrayList<>();
        this.context = context;
        if (!TypeData.isEmpty()) {
            TypeData.clear();
        }
        TypeData = get_budget_data(context, user, Life_Stage, budget_month);
//        getData1(Life_Stage, TypeData);
        Log.e("TypeData",TypeData.toString());
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            viewHolder.tv_budget_money.setText("预算 ￥"+String.valueOf(TypeData.get(position).getBudget_money()));
            viewHolder.tv_remaining_money.setText("余额 ￥"+String.valueOf(TypeData.get(position).getRemaining_money()));
            viewHolder.progressBar.setProgress(40);
            viewHolder.tv_budget_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "hhh", Toast.LENGTH_SHORT).show();
                }
            });
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
