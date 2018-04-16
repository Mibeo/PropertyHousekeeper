package com.example.bingjiazheng.propertyhousekeeper.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bingjia.zheng on 2018/4/16.
 */

public class BudgetSingleInfo implements Parcelable{
    private int _id;
    private String user;
    private int life;
    private String type;
    private double budget_money;//预算金额
    private double spended_money;//已经花费金额
    private double remaining_money;//剩余金额
    private String budget_month;//预算的月份

    public BudgetSingleInfo() {

    }

    public static final Creator<BudgetSingleInfo> CREATOR = new Creator<BudgetSingleInfo>() {
        @Override
        public BudgetSingleInfo createFromParcel(Parcel source) {
            BudgetSingleInfo budgetSingleInfo = new BudgetSingleInfo();
            budgetSingleInfo.set_id(source.readInt());
            budgetSingleInfo.setUser(source.readString());
            budgetSingleInfo.setLife(source.readInt());
            budgetSingleInfo.setType(source.readString());
            budgetSingleInfo.setBudget_money(source.readDouble());
            budgetSingleInfo.setSpended_money(source.readDouble());
            budgetSingleInfo.setRemaining_money(source.readDouble());
            budgetSingleInfo.setBudget_month(source.readString());
            return budgetSingleInfo;
        }

        @Override
        public BudgetSingleInfo[] newArray(int size) {
            return new BudgetSingleInfo[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBudget_money() {
        return budget_money;
    }

    public void setBudget_money(double budget_money) {
        this.budget_money = budget_money;
    }

    public double getSpended_money() {
        return spended_money;
    }

    public void setSpended_money(double spended_money) {
        this.spended_money = spended_money;
    }

    public double getRemaining_money() {
        return remaining_money;
    }

    public void setRemaining_money(double remaining_money) {
        this.remaining_money = remaining_money;
    }

    public String getBudget_month() {
        return budget_month;
    }

    public void setBudget_month(String budget_month) {
        this.budget_month = budget_month;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(user);
        dest.writeInt(life);
        dest.writeString(type);
        dest.writeDouble(budget_money);
        dest.writeDouble(spended_money);
        dest.writeDouble(remaining_money);
        dest.writeString(budget_month);
    }
}
