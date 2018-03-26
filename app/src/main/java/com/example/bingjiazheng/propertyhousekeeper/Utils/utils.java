package com.example.bingjiazheng.propertyhousekeeper.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by bingjia.zheng on 2018/3/26.
 */

public class utils {
    public static void hideSoftInput(Activity activity, Context context) {
        ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(activity.getCurrentFocus().
                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
