package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bingjia.zheng on 2018/3/28.
 */

public class MySpendActivity extends SpendIncomeContentActivity {

    @Override
    protected void init() {
        tv_Title.setText("我的支出");
        i=1;
    }
}
