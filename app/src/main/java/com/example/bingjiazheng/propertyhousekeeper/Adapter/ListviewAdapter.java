package com.example.bingjiazheng.propertyhousekeeper.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Activity.DataServer;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class ListviewAdapter extends BaseAdapter {
    private boolean dataisNull;
    private int Table;
    private int Life_Stage;

    public ListviewAdapter(Context context, String user, int Table,int Life_Stage) {
        this.context = context;
        this.Table = Table;
        this.Life_Stage = Life_Stage;
        if (Table == Constant.Spend_db) {
            data = DataServer.getData(context, "spend_db", user,Life_Stage);
        } else if (Table == Constant.Income_db) {
            data = DataServer.getData(context, "income_db", user,Life_Stage);
        } else if (Table == Constant.Flag_db) {
            data = DataServer.getData2(context, "flag_db", user,Life_Stage);
        }
        if (data == null) {
            dataisNull = true;
        }
    }

    private List<SingleInfo> data;
    private Context context;

    @Override
    public int getCount() {
        return dataisNull ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return dataisNull ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataisNull ? Long.parseLong(null) : data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            if (Table == Constant.Spend_db || Table == Constant.Income_db) {
                viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
                viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
            } else if (Table == Constant.Flag_db) {
                viewHolder.tv_text = convertView.findViewById(R.id.tv_type);
            }
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            if (Table == Constant.Spend_db || Table == Constant.Income_db) {
                viewHolder.tv_type.setText(data.get(position).getType());
                viewHolder.tv_money.setText(String.valueOf(data.get(position).getMoney()));
            } else if (Table == Constant.Flag_db) {
                viewHolder.tv_text.setText(data.get(position).getText());
            }
//            viewHolder.tv_number.setText(String.valueOf(data.get(position).getId()));
            viewHolder.tv_number.setText(String.valueOf(data.get(position).getId()));
            viewHolder.tv_date.setText(data.get(position).getDate());
            return convertView;
        } else {
            return null;
        }
    }

    static class ViewHolder {
        TextView tv_number;
        TextView tv_type;
        TextView tv_money;
        TextView tv_date;
        TextView tv_text;
    }
}
