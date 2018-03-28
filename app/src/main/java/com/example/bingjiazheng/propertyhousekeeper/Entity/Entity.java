package com.example.bingjiazheng.propertyhousekeeper.Entity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by bingjia.zheng on 2018/3/27.
 */

public class Entity{
    private int _id;
    private int life;
    private double money;
    private String time;
    private String type;
    private String site;
    private String payer_payee;
    private String remark;

    public Entity(int _id, int life, double money, String time, String type, String site, String payer_payee, String remark) {
        this._id = _id;
        this.life = life;
        this.money = money;
        this.time = time;
        this.type = type;
        this.site = site;
        this.payer_payee = payer_payee;
        this.remark = remark;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPayer_payee() {
        return payer_payee;
    }

    public void setPayer_payee(String payer_payee) {
        this.payer_payee = payer_payee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
