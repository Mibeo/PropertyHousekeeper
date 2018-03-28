package com.example.bingjiazheng.propertyhousekeeper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Activity.MySpendActivity;
import com.example.bingjiazheng.propertyhousekeeper.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class ListviewAdapter extends BaseAdapter {
    private List<String> data;
    public ListviewAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }
    String[] a ={"1、","食品烟酒","40.0元","2018-02-08"};
    private Context context;
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
//            viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
//            viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
//            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tv_number.setText(data.get(position));
//        viewHolder.tv_type.setText("食品烟酒");
//        viewHolder.tv_money.setText("40.0元");
//        viewHolder.tv_date.setText("2018-02-08");
        return convertView;
    }
    static class ViewHolder{
        TextView tv_number;
//        TextView tv_type;
//        TextView tv_money;
//        TextView tv_date;

    }
}
