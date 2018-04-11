package com.example.bingjiazheng.propertyhousekeeper.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bingjiazheng.propertyhousekeeper.Entity.MySQLiteHelper;
import com.example.bingjiazheng.propertyhousekeeper.R;
import com.example.bingjiazheng.propertyhousekeeper.Ui.CustomDatePicker;
import com.example.bingjiazheng.propertyhousekeeper.Utils.Constant;
import com.example.bingjiazheng.propertyhousekeeper.Utils.DbManger;
import com.github.mikephil.charting.animation.Easing;
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
import java.util.List;
import java.util.Locale;

import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData1;
import static com.example.bingjiazheng.propertyhousekeeper.Utils.DataManger.getData2;

/**
 * Created by bingjia.zheng on 2018/4/8.
 */

public class DataAnalysisActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private RelativeLayout iv_back;
    private TextView tv_Title, tv_month;
    private PieChart mPieChart;
    private MySQLiteHelper helper;
    private SQLiteDatabase sqLiteDatabase;
    private List<String> data;
    private int Life_Stage;
    private String time, date, month, year;
    private CustomDatePicker datePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_annlysis);
        initview();
    }

    private void initview() {
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
        month = time.split("-")[1];
        year = time.split("-")[0];
        tv_month.setText("当前年月 : " + year + " 年 " + month + " 月");
        //设置当前显示的日期
//        currentDate.setText(date);
//        tv_date.setText(date);
        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_month.setText("当前年月 : " + time.split("-")[0]+" 年 "+time.split("-")[1] + " 月");
//                tv_date.setText(time.split("-")[0]+"-"+time.split("-")[1]);
            }
        }, "2007-01-01 00:00", "2030-12-31 00:00");
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);


        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setText("数据分析");
        Life_Stage = getIntent().getIntExtra("Life_Stage", 0);
        helper = DbManger.getIntance(this);
        sqLiteDatabase = helper.getWritableDatabase();


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
        mPieChart.setCenterText(generateCenterSpannableText());
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

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(10, "优秀"));
        entries.add(new PieEntry(20, "满分"));
        entries.add(new PieEntry(30, "及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));
        entries.add(new PieEntry(5, "不及格"));

        //设置数据
        setData(entries);

//        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
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

    private List<String> getTypeData(int Table_Type) {
        data = new ArrayList<>();
        if (Table_Type == Constant.Spend_Table) {
            getData1(Life_Stage, data);
        } else if (Table_Type == Constant.Income_Table) {
            getData2(Life_Stage, data);
        }
        return data;
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
    private SpannableString generateCenterSpannableText() {
        //原文：MPAndroidChart\ndeveloped by Philipp Jahoda
        SpannableString s = new SpannableString("刘某人程序员\n我仿佛听到有人说我帅");
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
}
