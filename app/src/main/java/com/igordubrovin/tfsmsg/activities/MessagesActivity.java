package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.MessageAdapter;
import com.igordubrovin.tfsmsg.widgets.MessageEditor;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.utils.MessageIncomingItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private MessageEditor messageEditor;
    private Toolbar toolbar;

    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;

    private List<MessageItem> messageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        messageEditor = (MessageEditor) findViewById(R.id.message_editor);
        if (savedInstanceState == null)
            messageItems = new LinkedList<>();
        else{
            List<MessageItem> savedData = savedInstanceState.getParcelableArrayList(ProjectConstants.SAVED_LIST_MESSAGE_ITEMS);
            if (savedData != null)
                messageItems = new LinkedList<>(savedData);
        }

        initToolbar(getIntent().getStringExtra(ProjectConstants.DIALOG_TITLE));
        initRecyclerView();
        initMessageEditor();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<MessageItem> savedData = new ArrayList<>(messageItems);
        outState.putParcelableArrayList(ProjectConstants.SAVED_LIST_MESSAGE_ITEMS, (ArrayList<MessageItem>) savedData);
    }

    private void initToolbar(String tittle){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(tittle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initRecyclerView(){
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recycler_view_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        adapter = new MessageAdapter(messageItems, clickRecyclerMessageItem);
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
    }

    private void initMessageEditor(){
        messageEditor = (MessageEditor) findViewById(R.id.message_editor);
        messageEditor.setOnClickListenerSend(clickSend);
    }

    private View.OnClickListener clickSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = messageEditor.getText();
            ((LinkedList<MessageItem>)messageItems).addFirst(new MessageItem(message));
            adapter.notifyDataSetChanged();
            recyclerViewMessage.scrollToPosition(0);
            receiveMessage();
        }
    };

    private OnItemClickListener clickRecyclerMessageItem = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(MessagesActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
        }
    };

    private void receiveMessage(){
        String sender = "test test test test test test test test test";
        String message = "Test Test Test Test Test Test Test Test Test Test";
        ((LinkedList<MessageItem>)messageItems).addFirst(new MessageIncomingItem(message, sender));
        adapter.notifyDataSetChanged();
    }

}
