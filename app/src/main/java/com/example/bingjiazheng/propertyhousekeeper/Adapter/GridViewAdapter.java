package com.example.bingjiazheng.propertyhousekeeper.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.R;


/**
 * Created by bingjia.zheng on 2018/3/22.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int[] imagesID;
    private String[] grid_list;
    private int clickTemp = -1;
    public void setSelection(int position){
        clickTemp = position;
    }
    public GridViewAdapter(Context context, int[] imagesID, String[] grid_list) {
        this.context = context;
        this.imagesID = imagesID;
        this.grid_list = grid_list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imagesID.length;
    }

    @Override
    public Object getItem(int i) {
        return imagesID[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.gridview_item, null);
            viewHolder.grid_image = (ImageView) view.findViewById(R.id.grid_image);
            viewHolder.grid_text = (TextView) view.findViewById(R.id.grid_text1);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.grid_image.setImageResource(imagesID[i]);
        viewHolder.grid_text.setText(grid_list[i]);
        if(clickTemp == i){
            view.setBackgroundColor(Color.GRAY);
        }else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }

    public class ViewHolder {
        ImageView grid_image;
        TextView grid_text;

    }
}


