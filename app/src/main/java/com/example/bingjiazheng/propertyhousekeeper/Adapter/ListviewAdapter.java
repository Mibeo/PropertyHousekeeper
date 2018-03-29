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

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class ListviewAdapter extends BaseAdapter {
//    private List<String> data;
//    public ListviewAdapter(Context context, List<String> data){
//        this.context = context;
//        this.data = data;
//    }
    private boolean dataisNull;
    public ListviewAdapter(Context context,String user){
        this.context = context;
        data = DataServer.getData(context,"spend_db", user);
        if(data == null){
            dataisNull = true;
        }
//        Log.e("lot",data+"");
    }
    private List<SingleInfo> data;
//    String[] a ={"1、","食品烟酒","40.0元","2018-02-08"};
    private Context context;
    @Override
    public int getCount() {
        if(dataisNull){
            return 0;
        }else {
            return data.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(dataisNull){
            return null;
        }else {
            return data.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        if(dataisNull){
            return Long.parseLong(null);
        }else {
            return data.get(position).getId();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
            viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
            viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
//        Log.e("data",data.get(position).getType()+"");
        if(data!=null){
            viewHolder.tv_number.setText(String.valueOf(data.get(position).getId()));
            viewHolder.tv_type.setText(data.get(position).getType());
            viewHolder.tv_money.setText(String.valueOf(data.get(position).getMoney()));
            viewHolder.tv_date.setText(String.valueOf(data.get(position).getDate()));
            return convertView;
        }else {
            return null;
        }

    }
    static class ViewHolder{
        TextView tv_number;
        TextView tv_type;
        TextView tv_money;
        TextView tv_date;
    }
}
