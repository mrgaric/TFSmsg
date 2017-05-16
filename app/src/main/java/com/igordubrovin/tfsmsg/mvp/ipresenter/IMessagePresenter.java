package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.mvp.iview.IMessageView;

/**
 * Created by Игорь on 16.05.2017.
 */

public interface IMessagePresenter extends MvpPresenter<IMessageView> {
    void getMessageItems(DialogItem dialogItem);
    void updateDialogItem(DialogItem dialogItem);
    void sendMessageItem(MessageItem item);
    void insertMessageItem(MessageItem item);
}