package com.example.bingjiazheng.propertyhousekeeper.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class DataServer {
    private static List<String> data = new ArrayList<String>();

    public static List<String> getData(int offset, int maxnumber) {
        for (int i = 0; i < maxnumber; i++) {
            data.add("" + i);
        }
        return data;
    }
}
