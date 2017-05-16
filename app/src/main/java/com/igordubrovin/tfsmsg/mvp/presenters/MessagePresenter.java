package com.igordubrovin.tfsmsg.mvp.presenters;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.interfaces.ChatDbItemsListener;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IMessagePresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IMessageView;
import com.igordubrovin.tfsmsg.utils.DBFlowHelper;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Игорь on 16.05.2017.
 */

public class MessagePresenter extends MvpBasePresenter<IMessageView>
        implements IMessagePresenter,
        ChatDbItemsListener{

    private DBFlowHelper dbFlowHelper;
    private MessageItem messageItem;
    private List<MessageItem> messageItems;

    @Inject
    public MessagePresenter(DBFlowHelper dbFlowHelper){
        this.dbFlowHelper = dbFlowHelper;
    }

    @Override
    public void attachView(IMessageView view) {
        super.attachView(view);
        if (messageItem != null){
            getView().showAddedMessageItem(messageItem);
            messageItem = null;
        }
        if (messageItems != null){
            getView().showMessages(messageItems);
            messageItems = null;
        }
    }

    @Override
    public void getMessageItems(DialogItem dialogItem) {
        dbFlowHelper.getMessageItems(dialogItem, this);
    }

    @Override
    public void updateDialogItem(DialogItem dialogItem) {
        dbFlowHelper.saveItem(dialogItem);
    }

    @Override
    public void sendMessageItem(MessageItem item) {

    }

    @Override
    public void insertMessageItem(MessageItem item) {
        dbFlowHelper.saveItem(item, this);
    }

    @Override
    public void itemAdded(BaseModel item) {
        if (isViewAttached())
            getView().showAddedMessageItem((MessageItem) item);
        else
            messageItem = (MessageItem) item;

    }

    @Override
    public void itemsReceived(List<? extends BaseModel> items) {
        if (isViewAttached())
            getView().showMessages((List<MessageItem>) items);
        else
            messageItems = (List<MessageItem>) items;
    }

    @Override
    public void itemDeleted(BaseModel item) {

    }
}
