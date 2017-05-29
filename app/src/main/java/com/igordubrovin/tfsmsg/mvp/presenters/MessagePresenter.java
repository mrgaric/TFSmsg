package com.igordubrovin.tfsmsg.mvp.presenters;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.firebase.DialogItemValueListener;
import com.igordubrovin.tfsmsg.firebase.OnTransactionComplete;
import com.igordubrovin.tfsmsg.firebase.message.MessageRepository;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IMessagePresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IMessageView;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Игорь on 16.05.2017.
 */

public class MessagePresenter extends MvpBasePresenter<IMessageView>
        implements IMessagePresenter{

    private MessageItem messageItem;
    private List<MessageItem> messageItems;
    private MessageRepository messageRepository;

    @Inject
    public MessagePresenter(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
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
    public void getMessageItems(String dialogId) {
        messageRepository.getMessages(dialogId, new DialogItemValueListener<MessageItem>() {
            @Override
            public void onValue(List<MessageItem> items) {
                if (isViewAttached())
                    getView().showMessages(items);
                else
                    messageItems = items;
            }
        });
       // dbFlowHelper.getMessageItems(dialogItem, this);
    }

    @Override
    public void updateDialogItem(DialogItem dialogItem) {
        //dbFlowHelper.saveItem(dialogItem);
    }

    @Override
    public void sendMessageItem(String dialogId, MessageItem item) {
        messageRepository.addMessage(dialogId, item, new OnTransactionComplete<MessageItem>() {
            @Override
            public void onCommit(MessageItem result) {
                if (isViewAttached()){
                    getView().showAddedMessageItem(result);
                } else
                    messageItem = result;
            }
            @Override
            public void onAbort(Exception e) {

            }
        });
    }

    @Override
    public void insertMessageItem(MessageItem item) {
      //  dbFlowHelper.saveItem(item, this);
    }

    /*@Override
    public void itemAdded(MessageItem item) {
        if (isViewAttached())
            getView().showAddedMessageItem(item);
        else
            messageItem = item;

    }

    @Override
    public void itemsReceived(List<MessageItem> items) {
        if (isViewAttached())
            getView().showMessages(items);
        else
            messageItems = items;
    }*/

    /*@Override
    public void itemAdded(BaseModel item) {

    }

    @Override
    public void itemsReceived(List<? extends BaseModel> items) {

    }

    @Override
    public void itemDeleted(BaseModel item) {

    }*/
}
