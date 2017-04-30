package com.igordubrovin.tfsmsg.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.igordubrovin.tfsmsg.interfaces.ChatDbItemsListener;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Игорь on 28.04.2017.
 */

public class ChatDbFragment extends Fragment {
    private ChatDbItemsListener chatDbItemsListener;
    private BaseModel item;
    private List<? extends BaseModel> listItems;
    public static final String CHAT_DB_FRAGMENT_TAG = "chat_db_fragment_tag";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChatDbItemsListener){
            chatDbItemsListener = (ChatDbItemsListener) context;
            if (item != null){
                chatDbItemsListener.itemAdded(item);
                item = null;
            }
            if (listItems != null){
                chatDbItemsListener.itemsReceived(listItems);
                listItems = null;
            }
        } else throw new ClassCastException(context.toString()
                    + " must implement LoginListener");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        chatDbItemsListener = null;
    }

    public void returnItemAdded(BaseModel item){
        if (chatDbItemsListener != null){
            chatDbItemsListener.itemAdded(item);
        } else {
            this.item = item;
        }
    }

    public void returnListItemReceived(List<? extends BaseModel> listItems){
        if (chatDbItemsListener != null){
            chatDbItemsListener.itemsReceived(listItems);
        } else {
            this.listItems = listItems;
        }
    }

    public void returnItemDeleted(BaseModel item){
        //TODO
    }

}
