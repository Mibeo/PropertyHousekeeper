package com.example.bingjiazheng.propertyhousekeeper.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by bingjia.zheng on 2018/3/3.
 */

public class ToastUtil {
    // 构造方法私有化 不允许new对象
    private ToastUtil() {
    }

    /** Toast对象 */
    private static Toast toast = null ;
    /**
     * 显示Toast
     */
    @SuppressLint("ShowToast")
    public static void showText(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
