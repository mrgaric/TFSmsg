package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IMessageView;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;

/**
 * Created by Игорь on 16.05.2017.
 */

public interface IMessagePresenter extends MvpPresenter<IMessageView> {
    void getMessageItems(String dialogId);
    void updateDialogItem(DialogItem dialogItem);
    void sendMessageItem(String dialogId, MessageItem item);
    void insertMessageItem(MessageItem item);
}