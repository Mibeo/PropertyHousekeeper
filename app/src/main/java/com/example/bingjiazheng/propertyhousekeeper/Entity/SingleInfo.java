package com.example.bingjiazheng.propertyhousekeeper.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bingjia.zheng on 2018/3/29.
 */

public  class SingleInfo implements Parcelable{
    private int _id;
    private int id;
    private String user;
    private int life;
    private double money;
    private String date;
    private String type;
    private String address;
    private String payer_payee;
    private String remark;
    private String text;

    public SingleInfo(){

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(id);
        dest.writeString(user);
        dest.writeInt(life);
        dest.writeDouble(money);
        dest.writeString(date);
        dest.writeString(type);
        dest.writeString(address);
        dest.writeString(payer_payee);
        dest.writeString(remark);
        dest.writeString(text);
    }
    public static final Creator<SingleInfo> CREATOR = new Creator<SingleInfo>() {
        @Override
        public SingleInfo createFromParcel(Parcel source) {
            SingleInfo singleInfo = new SingleInfo();
            singleInfo.set_id(source.readInt());
            singleInfo.setId(source.readInt());
            singleInfo.setUser(source.readString());
            singleInfo.setLife(source.readInt());
            singleInfo.setMoney(source.readDouble());
            singleInfo.setDate(source.readString());
            singleInfo.setType(source.readString());
            singleInfo.setAddress(source.readString());
            singleInfo.setPayer_payee(source.readString());
            singleInfo.setRemark(source.readString());
            singleInfo.setText(source.readString());
            return singleInfo;
        }

        @Override
        public SingleInfo[] newArray(int size) {
            return new SingleInfo[size];
        }/*private int id;
        private String user;
        private int life;
        private double money;
        private String date;
        private String type;
        private String address;
        private String payer_payee;
        private String remark;*/
    };
}
