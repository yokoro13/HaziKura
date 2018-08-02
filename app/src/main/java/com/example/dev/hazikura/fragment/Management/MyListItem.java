package com.example.dev.hazikura.fragment.Management;

import android.util.Log;

/**
 * Created by yokoro
 **/

public class MyListItem {

    protected int id;
    protected String date;
    protected String content;
    protected String number;
    protected String amount;

    public MyListItem(int id, String date, String content, String amount){
        this.id = id;
        this.date = date;
        this.content = content;
        this.amount = amount;
    }

    public MyListItem(int id, String date, String content, String number, String amount){
        this.id = id;
        this.date = date;
        this.content = content;
        this.number = number;
        this.amount = amount;
    }

    public int getId(){
        Log.d("取得ID：", String.valueOf(id));
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getNumber(){ return number; }

    public String getAmount() {
        return amount;
    }

}
