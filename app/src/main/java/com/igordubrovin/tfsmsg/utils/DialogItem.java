package com.igordubrovin.tfsmsg.utils;

import org.parceler.Parcel;

@Parcel(analyze = DialogItem.class)
public class DialogItem {
    String title;
    String desc;
    String time;
    String date;
    String lastMessage;

    public DialogItem(){}

    public synchronized String getTitle() {
        return title;
    }

    public synchronized void setTitle(String title) {
        this.title = title;
    }

    public synchronized String getDesc() {
        return desc;
    }

    public synchronized void setDesc(String desc) {
        this.desc = desc;
    }

    public synchronized String getTime() {
        return time;
    }

    public synchronized void setTime(String time) {
        this.time = time;
    }

    public synchronized String getDate() {
        return date;
    }

    public synchronized void setDate(String date) {
        this.date = date;
    }

    public synchronized String getLastMessage() {
        return lastMessage;
    }

    public synchronized void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}