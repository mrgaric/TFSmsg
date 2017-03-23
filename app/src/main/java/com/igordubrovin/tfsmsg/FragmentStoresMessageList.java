package com.igordubrovin.tfsmsg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Игорь on 23.03.2017.
 */

public class FragmentStoresMessageList extends Fragment {

    private List<MessageItem> messageList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }
    public List<MessageItem> getMessageList(){
        return messageList;
    }

    public void setMessageList(List<MessageItem> messageList) {
        this.messageList = messageList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
