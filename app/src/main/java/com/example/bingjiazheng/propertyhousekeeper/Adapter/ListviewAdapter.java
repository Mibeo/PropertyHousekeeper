package com.example.bingjiazheng.propertyhousekeeper.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.total_money;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class ListviewAdapter extends BaseAdapter {
    private boolean dataisNull = false;
    private boolean HashdataisNull = false;
    private int Table;
    private int Life_Stage;
    private HashMap<String, Double> HashData;
    private String selector_date;
    private List<String> list_key = DataServer.list_key;

    public ListviewAdapter(Context context, String user, int Life_Stage, String selector_date) {
        this.context = context;
        this.Life_Stage = Life_Stage;
        this.selector_date = selector_date;
        if (HashData!=null) {
            HashData.clear();
        }
        HashData = DataServer.get_spend_Data(context, user, "spend_db", Life_Stage, selector_date);
//        Log.e("HashData",HashData.toString());
        if (HashData.isEmpty()) {
            HashdataisNull = true;
        }
    }

    public ListviewAdapter(Context context, String user, int Table, int Life_Stage) {
        this.context = context;
        this.Table = Table;
        this.Life_Stage = Life_Stage;
        if (Table == Constant.Spend_db) {
            data = DataServer.getData(context, "spend_db", user, Life_Stage);
        } else if (Table == Constant.Income_db) {
            data = DataServer.getData(context, "income_db", user, Life_Stage);
        } else if (Table == Constant.Flag_db) {
            data = DataServer.getData2(context, "flag_db", user, Life_Stage);
        }
        if (data == null) {
            dataisNull = true;
        }
    }

    private List<SingleInfo> data;
    private Context context;

    @Override
    public int getCount() {
        if (selector_date == null) {
            return dataisNull ? 0 : data.size();
        } else {
            return HashdataisNull ? 0 : HashData.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (selector_date == null) {
            return dataisNull ? null : data.get(position);
        } else {
            return HashdataisNull ? null : HashData.get(list_key.get(position));
        }
    }

    @Override
    public long getItemId(int position) {
        if (selector_date == null) {
            return position;
//            return dataisNull ? Long.parseLong(null) : data.get(position).getId();
        } else {
//            return HashdataisNull ? Long.parseLong(null) : HashData.get(list_key.get(position)).longValue();
            return position;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            if (selector_date == null) {
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
                viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
                viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
                viewHolder.tv_percent = convertView.findViewById(R.id.tv_date);
                convertView.setTag(viewHolder);
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (selector_date == null) {
            if (data != null) {
                if (Table == Constant.Spend_db || Table == Constant.Income_db) {
                    viewHolder.tv_type.setText(data.get(position).getType());
                    viewHolder.tv_money.setText(data.get(position).getMoney()+"元");
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
        } else {
            if (HashData != null) {
                viewHolder.tv_type.setText(list_key.get(position));
//                Log.e("test",1+"");
//                Log.e("list_key", list_key.get(position));
                viewHolder.tv_money.setText(String.valueOf(HashData.get(list_key.get(position))));
//                Log.e("test",2+"");
                NumberFormat nf = new DecimalFormat("##.##");
                Double d = HashData.get(list_key.get(position)) / total_money * 100;
//                Log.e("test",3+"");
//                Log.e("total_money", total_money + "");
                String string = nf.format(d);
//                Log.e("test",4+"");
                viewHolder.tv_percent.setText(string + "%");
//                Log.e("test",5+"");
                return convertView;
            } else {
                return null;
            }
        }
       /* NumberFormat nf = new DecimalFormat("##.##");
        Double d = 554545.4545454;
        String str = nf.format(d);*/
    }

    static class ViewHolder {
        TextView tv_number;
        TextView tv_type;
        TextView tv_money;
        TextView tv_date;
        TextView tv_text;
        TextView tv_percent;
    }
}
