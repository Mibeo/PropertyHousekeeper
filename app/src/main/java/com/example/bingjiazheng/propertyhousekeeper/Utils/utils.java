package com.example.bingjiazheng.propertyhousekeeper.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class utils {
    public static void hideSoftInput(Activity activity, Context context) {
        ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(activity.getCurrentFocus().
                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
