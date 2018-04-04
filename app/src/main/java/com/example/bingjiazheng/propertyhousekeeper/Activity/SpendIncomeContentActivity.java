package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Adapter.ListviewAdapter;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.ToastUtil.showText;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public abstract class SpendIncomeContentActivity extends AppCompatActivity {
    private RelativeLayout iv_back;
    private View v;
    private Handler handler;
    private ListView listView;
    private ListviewAdapter listviewAdapter;
    private String user;
    private Spinner spinner;
    protected TextView tv_Title;
    private int life;
    protected int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_spend_list);
        initView();
    }

    @Override
    protected void onDestroy() {
        listviewAdapter.notifyDataSetChanged();
        super.onDestroy();
    }

    public List<String> getDataSource() {
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("学生");
        spinnerList.add("工作未婚");
        spinnerList.add("工作已婚");
        spinnerList.add("退休");
        return spinnerList;
    }

    protected abstract void init();

    private void initView() {
        user = getIntent().getStringExtra("user");
        life = getIntent().getIntExtra("life", 0);
        v = this.getLayoutInflater().inflate(R.layout.list_refresh, null);
        listView = (ListView) super.findViewById(R.id.listview);
        tv_Title = findViewById(R.id.tv_Title);
        init();
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item2, getDataSource());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(spinnerAadapter);
        spinner.setSelection(life - 1);
        //得到数据
//        data=DataServer.getData();
        //实习化ArrayAdapter对象
        listviewAdapter = new ListviewAdapter(this, user, i);

//        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);
        //添加listview的脚跟视图，这个方法必须在listview.setAdapter()方法之前，否则无法显示视图
        listView.addFooterView(v);
        //添加数据
        listView.setAdapter(listviewAdapter);
        //当下一页的数据加载完成之后移除改视图
        listView.removeFooterView(v);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingleInfo singleInfo = (SingleInfo) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ModifyDeleteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("singleInfo", singleInfo);
                intent.putExtra("user",user);
                intent.putExtra("i",i);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==2){
            listviewAdapter = new ListviewAdapter(this, user, i);
            listView.setAdapter(listviewAdapter);
            showText(this,"hello");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
