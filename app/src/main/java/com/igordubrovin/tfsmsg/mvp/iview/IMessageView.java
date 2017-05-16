package com.igordubrovin.tfsmsg.mvp.iview;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.igordubrovin.tfsmsg.db.MessageItem;

import java.util.List;

/**
 * Created by Игорь on 16.05.2017.
 */

public interface IMessageView extends MvpView {
    void showMessages(List<MessageItem> messageItems);
    void showAddedMessageItem(MessageItem item);
    void showErrorSendMessageItem();
}
