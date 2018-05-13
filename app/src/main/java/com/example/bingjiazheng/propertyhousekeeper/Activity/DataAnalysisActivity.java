package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.CustomDatePicker;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getData3;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataServer.getDataSource;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData2;

/**
 * Created by bingjia.zheng on 2018/4/8.
 */

public class DataAnalysisActivity extends AppCompatActivity implements OnChartValueSelectedListener, View.OnClickListener {
    private RelativeLayout iv_back;
    private TextView tv_Title, tv_month;
    private PieChart mPieChart;
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private List<String> Type_data;
    private int Life_Stage;
    private String time, date, month, year, selector_date;
    private CustomDatePicker datePicker;
    private Button bt_spend, bt_income, bt_spend_details, bt_invest_analysis,bt_budget;
    private String Table;
    private List<SingleInfo> data;
    private String user;
    private Map<String, Double> HashData;
    private double total_money = 0.0;
    private ArrayList<PieEntry> entries;
    private Spinner spinner_life;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_annlysis);
        initview();
    }

    private void initview() {
        user = getIntent().getStringExtra("user");
        Life_Stage = getIntent().getIntExtra("Life_Stage", 0);
//        user = "123";
//        Life_Stage = 1;
        tv_month = findViewById(R.id.tv_month);
        tv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(date);
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        year = time.split("-")[0];
        month = time.split("-")[1];
        tv_month.setText("当前月份 : " + year + " 年 " + month + " 月");
        selector_date = year + "-" + month;
        //设置当前显示的日期
//        currentDate.setText(date);
//        tv_date.setText(date);
        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_month.setText("当前月份 : " + time.split("-")[0] + " 年 " + time.split("-")[1] + " 月");
                selector_date = time.split("-")[0] + "-" + time.split("-")[1];
                mPieChart.setCenterText(null);
                if (!bt_spend.isEnabled()) {
                    spend_data_deal(Life_Stage, selector_date);
                } else if (!bt_income.isEnabled()) {
                    income_data_deal(Life_Stage, selector_date);
                }
//                Log.e("time", selector_date);
                //                tv_date.setText(time.split("-")[0]+"-"+time.split("-")[1]);
            }
        }, "2007-01-01 00:00", "2030-12-31 00:00");
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);

        data = new ArrayList<>();
        HashData = new HashMap<String, Double>();
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("数据分析");

        bt_spend = findViewById(R.id.bt_spend);
        bt_spend.setOnClickListener(this);
        bt_income = findViewById(R.id.bt_income);
        bt_income.setOnClickListener(this);
        bt_spend.setEnabled(false);
        bt_income.setEnabled(true);
        bt_spend_details = findViewById(R.id.bt_spend_details);
        bt_spend_details.setOnClickListener(this);
        bt_invest_analysis = findViewById(R.id.bt_invest_analysis);
        bt_invest_analysis.setOnClickListener(this);
        bt_budget = findViewById(R.id.bt_budget);
        bt_budget.setOnClickListener(this);
        spinner_life = findViewById(R.id.spinner_life);
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item2, getDataSource());
        spinnerAadapter
                .setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_life.setAdapter(spinnerAadapter);
        spinner_life.setSelection(Life_Stage - 1);
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();
        spinner_life.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = (String) spinner_life.getItemAtPosition(position);//从spinner中获取被选择的数据
                switch (data) {
                    case "学生":
                        Life_Stage = 1;
                        if (!bt_spend.isEnabled()) {
                            spend_data_deal(Life_Stage, selector_date);
                        } else if (!bt_income.isEnabled()) {
                            income_data_deal(Life_Stage, selector_date);
                        }
                        break;
                    case "工作未婚":
                        Life_Stage = 2;
                        if (!bt_spend.isEnabled()) {
                            spend_data_deal(Life_Stage, selector_date);
                        } else if (!bt_income.isEnabled()) {
                            income_data_deal(Life_Stage, selector_date);
                        }
                        break;
                    case "工作已婚":
                        Life_Stage = 3;
                        if (!bt_spend.isEnabled()) {
                            spend_data_deal(Life_Stage, selector_date);
                        } else if (!bt_income.isEnabled()) {
                            income_data_deal(Life_Stage, selector_date);
                        }
                        break;
                    case "退休":
                        Life_Stage = 4;
                        if (!bt_spend.isEnabled()) {
                            spend_data_deal(Life_Stage, selector_date);
                        } else if (!bt_income.isEnabled()) {
                            income_data_deal(Life_Stage, selector_date);
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //饼状图
        mPieChart = (PieChart) findViewById(R.id.mPieChart);
        //设置使用百分比
        mPieChart.setUsePercentValues(true);
        //设置描述信息是否显示
        mPieChart.getDescription().setEnabled(false);
        //设置圆环距离屏幕上下上下左右的距离
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
//        mPieChart.setCenterText(generateCenterSpannableText());
        //设置是否显示圆环中间的洞
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);//设置中间洞颜色
        //设置圆环透明度及半径
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        //设置圆环中间洞的半径
        mPieChart.setHoleRadius(45f);
        mPieChart.setTransparentCircleRadius(45f);
        //是否显示洞中间文本
        mPieChart.setDrawCenterText(true);
        //触摸是否可以旋转以及松手后旋转的度数
        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);

//        dealdata(Table, selector_date);
        //模拟数据
        entries = new ArrayList<PieEntry>();
        /*Iterator iter = map.entrySet().iterator();
　　while (iter.hasNext()) {
　　Map.Entry entry = (Map.Entry) iter.next();
　　Object key = entry.getKey();
　　Object val = entry.getValue();
　　}*/
       /* Iterator iterator = HashData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            entries.add(new PieEntry((Float.valueOf(entry.getValue()+"") / Float.valueOf(total_money+"") * 100), entry.getKey()+""));
        }*/
        /*while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
//            entries.add(new PieEntry((float) ((float)entry.getValue() / i * 100), entry.getKey()));
//            Log.e("entry",(float) ((float)entry.getValue() / i * 100)+" "+entry.getKey()+"");
        }*/
        /*entries.add(new PieEntry(10, "优秀"));
        entries.add(new PieEntry(20, "满分"));
        entries.add(new PieEntry(30, "及格"));*/

        //设置数据
//        setData(entries);

//        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        spend_data_deal(Life_Stage, selector_date);
        Legend l = mPieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
        l.setWordWrapEnabled(true);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.setEntryLabelTextSize(12f);

    }

    private void income_data_deal(int Life_Stage, String selector_date) {
        total_money = 0.0;
        Table = "income_db";
        if(data!=null){
            data.clear();
        }
        if(HashData!=null){
            HashData.clear();
        }
        entries.clear();

        data = getData3(this, Table, user, Life_Stage, selector_date);
        if(data!=null){
            for (int i = 0; i < data.size(); i++) {
                if (HashData.containsKey(data.get(i).getType())) {
                    total_money += data.get(i).getMoney();
                    Double aDouble = HashData.get(data.get(i).getType());
                    aDouble = aDouble + data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), aDouble);
                } else {
                    total_money += data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), data.get(i).getMoney());
                }
            }
            Iterator iterator = HashData.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                entries.add(new PieEntry((Float.valueOf(entry.getValue() + "") / Float.valueOf(total_money + "") * 100), entry.getKey() + ""));
            }
            mPieChart.invalidate();
            setData(entries);
            Log.e("total_money",total_money+"");

//        generateCenterSpannableText("income_total");
        }
        mPieChart.setCenterText(generateCenterSpannableText("income_total", total_money));
        mPieChart.invalidate();
    }

    private void spend_data_deal(int Life_Stage, String selector_date) {
        total_money = 0.0;
        Table = "spend_db";
        if(data!=null){
            data.clear();
        }
        if(HashData!=null){
            HashData.clear();
        }
        entries.clear();

        data = getData3(this, Table, user, Life_Stage, selector_date);
        if(data!=null){
            for (int i = 0; i < data.size(); i++) {
                if (HashData.containsKey(data.get(i).getType())) {
                    total_money += data.get(i).getMoney();
                    Double aDouble = HashData.get(data.get(i).getType());
                    aDouble = aDouble + data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), aDouble);
                } else {
                    total_money += data.get(i).getMoney();
                    HashData.put(data.get(i).getType(), data.get(i).getMoney());
                }
            }
            Iterator iterator = HashData.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                entries.add(new PieEntry((Float.valueOf(entry.getValue() + "") / Float.valueOf(total_money + "") * 100), entry.getKey() + ""));
            }
//            mPieChart.invalidate();
            setData(entries);
            Log.e("total_money",total_money+"");

        }
        mPieChart.setCenterText(generateCenterSpannableText("spend_total", total_money));
        mPieChart.invalidate();
//        generateCenterSpannableText("spend_total");
    }

    private void dealdata(String Table, String selector_date) {
        data = new ArrayList<>();
        HashData = new HashMap<String, Double>();
        data = getData3(this, Table, user, Life_Stage, selector_date);
        for (int i = 0; i < data.size(); i++) {
            if (HashData.containsKey(data.get(i).getType())) {
                total_money += data.get(i).getMoney();
                Double aDouble = HashData.get(data.get(i).getType());
                aDouble = aDouble + data.get(i).getMoney();
                HashData.put(data.get(i).getType(), aDouble);
            } else {
                total_money += data.get(i).getMoney();
                HashData.put(data.get(i).getType(), data.get(i).getMoney());
            }
        }
    }

    private List<String> getTypeData(int Table_Type) {
        Type_data = new ArrayList<>();
        if (Table_Type == Constant.Spend_Table) {
            getData1(Life_Stage, Type_data);
        } else if (Table_Type == Constant.Income_Table) {
            getData2(Life_Stage, Type_data);
        }
        return Type_data;
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    //设置中间文字
    private SpannableString generateCenterSpannableText(String Type_Text,double total_money) {
        //原文：MPAndroidChart\ndeveloped by Philipp Jahoda
//        SpannableString s = new SpannableString("刘某人程序员\n我仿佛听到有人说我帅");
        SpannableString s = null;
        if (Type_Text.equals("spend_total")) {
            s = new SpannableString("总支出\n" + total_money + "元");
        } else if (Type_Text.equals("income_total")) {
            s = new SpannableString("总收入\n" + total_money + "元");
        }

        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        // s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        // s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_spend:
                bt_spend.setEnabled(false);
                bt_income.setEnabled(true);
                spend_data_deal(Life_Stage, selector_date);
                break;
            case R.id.bt_income:
                bt_spend.setEnabled(true);
                bt_income.setEnabled(false);
                income_data_deal(Life_Stage, selector_date);
                break;
            case R.id.bt_spend_details:
                Intent intent = new Intent(this,SpendPlanActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("Life_Stage",Life_Stage);
                startActivity(intent);
                break;
            case R.id.bt_budget:
                Intent intent1 = new Intent(this,MyBudgetActivity.class);
                intent1.putExtra("user",user);
                intent1.putExtra("Life_Stage",Life_Stage);
                startActivity(intent1);
                break;
            case R.id.bt_invest_analysis:
                Intent intent2 = new Intent(this,InvestAnalysisActivity.class);
                intent2.putExtra("user",user);
                intent2.putExtra("Life_Stage",Life_Stage);
                startActivity(intent2);
                break;
        }
    }

    /*public List<String> getDataSource() {
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("学生");
        spinnerList.add("工作未婚");
        spinnerList.add("工作已婚");
        spinnerList.add("退休");
        return spinnerList;
    }*/

}
