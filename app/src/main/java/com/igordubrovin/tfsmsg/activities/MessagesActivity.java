package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.MessageAdapter;
import com.igordubrovin.tfsmsg.fragments.SendMessageTaskFragment;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.loaders.MessageLoader;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.igordubrovin.tfsmsg.widgets.MessageEditor;

import java.util.List;

public class MessagesActivity extends AppCompatActivity
        implements SendMessageTaskFragment.MessageSentListener,
        LoaderManager.LoaderCallbacks<List<MessageItem>>{

    private MessageEditor messageEditor;
    private Toolbar toolbar;
    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;
    private SendMessageTaskFragment sendMessageTaskFragment;
    Loader messageLoader;

    private static final int ID_MESSAGE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        messageEditor = (MessageEditor) findViewById(R.id.message_editor);

        initToolbar(getIntent().getStringExtra(ProjectConstants.DIALOG_TITLE));
        initRecyclerView();
        initMessageEditor();
        initFragment();

        messageLoader = getSupportLoaderManager().initLoader(ID_MESSAGE_LOADER, null, this);

        if (savedInstanceState == null) {
            messageLoader.forceLoad();
        }
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
        adapter = new MessageAdapter(clickRecyclerMessageItem);
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
    }

    private void initMessageEditor(){
        messageEditor = (MessageEditor) findViewById(R.id.message_editor);
        messageEditor.setOnClickListenerSend(clickSend);
    }

    private void initFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        sendMessageTaskFragment = (SendMessageTaskFragment) supportFragmentManager.findFragmentByTag(SendMessageTaskFragment.TAG);
        if (sendMessageTaskFragment == null) {
            sendMessageTaskFragment = new SendMessageTaskFragment();
            supportFragmentManager.beginTransaction().add(sendMessageTaskFragment, SendMessageTaskFragment.TAG).commit();
        }
    }

    private View.OnClickListener clickSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String messageText = messageEditor.getText();
            MessageItem message = new MessageItem(messageText);
            recyclerViewMessage.scrollToPosition(0);
            sendMessageTaskFragment.startSend(message);
        }
    };

    private OnItemClickListener clickRecyclerMessageItem = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(MessagesActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void messageSent(Boolean success, MessageItem messageItem) {
        if (success){
            Toast.makeText(this, "messageSent", Toast.LENGTH_SHORT).show();
            ((MessageLoader)messageLoader).addSentMessage(messageItem);
        } else {

        }
    }

    @Override
    public Loader<List<MessageItem>> onCreateLoader(int id, Bundle args) {
        return new MessageLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<MessageItem>> loader, List<MessageItem> data) {
        if (data != null){
            ((MessageAdapter)adapter).swapData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MessageItem>> loader) {

    }
}
