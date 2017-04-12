package com.igordubrovin.tfsmsg.activities;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
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
import com.igordubrovin.tfsmsg.service.ReceiveMessageService;
import com.igordubrovin.tfsmsg.utils.MessageIncomingItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.igordubrovin.tfsmsg.widgets.MessageEditor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity
        implements SendMessageTaskFragment.MessageSentListener{

    public static final String PENDING_INTENT = "pi";
    public static final String EXTRA_SUCCESS = "extra_success";
    public static final String CREDENTIALS = "credentials";
    public static final int LOGIN_REQUEST_CODE = 1;

    private MessageEditor messageEditor;
    private Toolbar toolbar;
    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;
    private List<MessageItem> messageItems;

    private SendMessageTaskFragment sendMessageTaskFragment;
    private ServiceConnection sCOnn;
    private boolean bound;
    private ReceiveMessageService receiveMessageService;
    Intent intentForService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        messageEditor = (MessageEditor) findViewById(R.id.message_editor);

        Intent data = new Intent().putExtra(CREDENTIALS, new String[]{});
        PendingIntent pi = createPendingResult(LOGIN_REQUEST_CODE, data, 0);
        intentForService = new Intent(MessagesActivity.this, ReceiveMessageService.class).putExtra(PENDING_INTENT, pi);
        sCOnn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                receiveMessageService = ((ReceiveMessageService.AnswerBinder)service).gerService();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };

        if (savedInstanceState == null) {
            messageItems = new LinkedList<>();
            startService(intentForService);
        }
        else{
            List<MessageItem> savedData = savedInstanceState.getParcelableArrayList(ProjectConstants.SAVED_LIST_MESSAGE_ITEMS);
            if (savedData != null)
                messageItems = new LinkedList<>(savedData);
        }

        bindService(intentForService, sCOnn, 0);

        initToolbar(getIntent().getStringExtra(ProjectConstants.DIALOG_TITLE));
        initRecyclerView();
        initMessageEditor();
        initFragment();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(sCOnn);
            bound = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(intentForService);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<MessageItem> savedData = new ArrayList<>(messageItems);
        outState.putParcelableArrayList(ProjectConstants.SAVED_LIST_MESSAGE_ITEMS, (ArrayList<MessageItem>) savedData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                receiveMessage(data.getStringExtra(ProjectConstants.SERVICE_SENDER_MESSAGE), data.getStringExtra(ProjectConstants.SERVICE_MESSAGE));
            }
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
        adapter = new MessageAdapter(messageItems, clickRecyclerMessageItem);
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
            String message = messageEditor.getText();
            ((LinkedList<MessageItem>)messageItems).addFirst(new MessageItem(message));
            adapter.notifyDataSetChanged();
            recyclerViewMessage.scrollToPosition(0);
            sendMessageTaskFragment.startSend(message);
            if (bound)
                receiveMessageService.sendAnswer();
        }
    };

    private OnItemClickListener clickRecyclerMessageItem = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(MessagesActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
        }
    };

    private void receiveMessage(String sender, String message){
        ((LinkedList<MessageItem>)messageItems).addFirst(new MessageIncomingItem(message, sender));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void messageSent(Boolean success) {
        if (success){
            Toast.makeText(this, "messageSent", Toast.LENGTH_SHORT).show();
        } else {

        }
    }
}
