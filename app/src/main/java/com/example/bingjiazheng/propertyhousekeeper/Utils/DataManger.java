package com.example.bingjiazheng.propertyhousekeeper.Utils;

import com.example.bingjiazheng.propertyhousekeeper.Entity.SingleInfo;

import java.util.List;

/**
 * Created by bingjia.zheng on 2018/4/4.
 */

public class DataManger {
    public static List<String> data;
    public static void getData1(int Life_Stage,List<String> data) {
        switch (Life_Stage){
            case 1:
                student1(data);
                break;
            case 2:
                workunmarried1(data);
                break;
            case 3:
                workmarried1(data);
                break;
            case 4:
                retire1(data);
                break;
        }
    }
    private static void retire2(List<String> datas) {
        datas.add("退休工资");
        datas.add("养老保险");
        datas.add("儿女给予");
        datas.add("利息收入");
        datas.add("其他");
    }

    private static void workmarried2(List<String> datas) {
        datas.add("个人工资");
        datas.add("父母给予");
        datas.add("业余兼职");
        datas.add("股票投资");
        datas.add("利息收入");
        datas.add("其他");
    }

    private static void workunmarried2(List<String> datas) {
        datas.add("个人工资");
        datas.add("父母给予");
        datas.add("业余兼职");
        datas.add("股票投资");
        datas.add("利息收入");
        datas.add("其他");
    }
    private static void student2(List<String> datas){
        datas.add("父母给予");
        datas.add("学校奖励");
        datas.add("课外奖励");
        datas.add("其他");
    }

    private static void retire1(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("居家物业");
        datas.add("儿女费用");
        datas.add("衣服饰品");
        datas.add("休闲娱乐");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("人情往来");
        datas.add("学习进修");
        datas.add("医疗保健");
        datas.add("其他");
    }

    private static void workmarried1(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("居家物业");
        datas.add("儿女费用");
        datas.add("衣服饰品");
        datas.add("休闲娱乐");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("人情往来");
        datas.add("学习进修");
        datas.add("父母费用");
        datas.add("医疗保健");
        datas.add("其他");
    }

    private static void workunmarried1(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("居家物业");
        datas.add("衣服饰品");
        datas.add("休闲娱乐");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("人情往来");
        datas.add("学习进修");
        datas.add("医疗保健");
        datas.add("恋爱开销");
        datas.add("其他");
    }

    private static void student1(List<String> datas) {
        datas.add("食品烟酒");
        datas.add("衣服饰品");
        datas.add("生活用品");
        datas.add("行车交通");
        datas.add("交流通讯");
        datas.add("休闲娱乐");
        datas.add("学习进修");
        datas.add("医疗保健");
        datas.add("恋爱开销");
        datas.add("其他");
    }

    public static void getData2(int Life_Stage,List<String> datas) {
        switch (Life_Stage) {
            case 1:
                student2(datas);
                break;
            case 2:
                workunmarried2(datas);
                break;
            case 3:
                workmarried2(datas);
                break;
            case 4:
                retire2(datas);
                break;
        }
    }
}
