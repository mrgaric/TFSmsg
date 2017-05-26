package com.igordubrovin.tfsmsg.utils;

/**
 * Created by Игорь on 18.03.2017.
 */

public class MessageItem {
    long id;
    String messageText;
    String idAuthor;
    String time;
    String date;
    DialogItem dialogItem;

    public MessageItem(){}

    public synchronized String getMessageText() {
        return messageText;
    }

    public synchronized void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public synchronized String getIdAuthor() {
        return idAuthor;
    }

    public synchronized void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
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

    public synchronized DialogItem getDialogItem() {
        return dialogItem;
    }

    public synchronized void setDialogItem(DialogItem dialogItem) {
        this.dialogItem = dialogItem;
    }

    public synchronized long getId() {
        return id;
    }

}
