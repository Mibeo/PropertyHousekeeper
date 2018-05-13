package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getData;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getDataSource;

/**
 * Created by bingjia.zheng on 2018/5/3.
 */

public class InvestAnalysisActivity extends AppCompatActivity {
    private TextView tv_Title;
    private RelativeLayout iv_back;
    private Spinner spinner_life;
    private Spinner spinner_type;
    private int type;
    private String user;
    private  int Life_Stage;
    private TextView tv_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_analysis);
        initview();
    }

    private void initview() {
        user = getIntent().getStringExtra("user");
        Life_Stage = getIntent().getIntExtra("Life_Stage", 0);
        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("投资分析");
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_text = findViewById(R.id.tv_text);
        spinner_life = findViewById(R.id.spinner_life);
        ArrayAdapter<String> spinnerAadapter1 = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item2, getDataSource());
        spinnerAadapter1
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_life.setAdapter(spinnerAadapter1);
        spinner_life.setSelection(Life_Stage - 1);

        spinner_type = findViewById(R.id.spinner_type);
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item, getType());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_type.setAdapter(spinnerAadapter);
        spinner_type.setSelection(1);
        type = 2;
        spinner_life.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) spinner_life.getItemAtPosition(position);//从spinner中获取被选择的数据
                switch (data) {
                    case "学生":
                        Life_Stage = 1;
                        break;
                    case "工作未婚":
                        Life_Stage = 2;
                        break;
                    case "工作已婚":
                        Life_Stage = 3;
                        break;
                    case "退休":
                        Life_Stage = 4;
                        break;
                }
                tv_text.setText(getData(Life_Stage,type));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) spinner_type.getItemAtPosition(position);//从spinner中获取被选择的数据
                switch (data) {
                    case "积极型":
                        type = 1;
                        break;
                    case "中庸型":
                        type = 2;
                        break;
                    case "保守型":
                        type = 3;
                        break;
                }
                tv_text.setText(getData(Life_Stage,type));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static List<String> getType() {
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("积极型");
        spinnerList.add("中庸型");
        spinnerList.add("保守型");
        return spinnerList;
    }
}
