package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.MessageAdapter;
import com.igordubrovin.tfsmsg.db.ChatDbHelper;
import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.fragments.ChatDbFragment;
import com.igordubrovin.tfsmsg.fragments.SendMessageTaskFragment;
import com.igordubrovin.tfsmsg.interfaces.ChatDbItemsListener;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.loaders.MessageLoader;
import com.igordubrovin.tfsmsg.utils.DateHelper;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.igordubrovin.tfsmsg.widgets.MessageEditor;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcels;

import java.util.LinkedList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity
        implements SendMessageTaskFragment.MessageSentListener,
        ChatDbItemsListener,
        LoaderManager.LoaderCallbacks<MessageItem>{

    private MessageEditor messageEditor;
    private Toolbar toolbar;
    private TextView tvTitle;
    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;
    private SendMessageTaskFragment sendMessageTaskFragment;
    private DialogItem dialogItem;
    private ChatDbFragment chatDbFragment;
    private String login;

    private static final int ID_MESSAGE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        String title;
        Parcelable parcelable;
        messageEditor = (MessageEditor) findViewById(R.id.message_editor);
        if (savedInstanceState == null) {
            parcelable = getIntent().getParcelableExtra(ProjectConstants.DIALOG_ITEM_INTENT);
            title = getIntent().getStringExtra(ProjectConstants.DIALOG_TITLE);
            login = getIntent().getStringExtra(ProjectConstants.USER_LOGIN);
            getSupportLoaderManager().initLoader(ID_MESSAGE_LOADER, null, this);
        } else {
            parcelable = savedInstanceState.getParcelable(ProjectConstants.DIALOG_ITEM_INTENT);
            title = savedInstanceState.getString(ProjectConstants.DIALOG_TITLE, "Title");
            login = savedInstanceState.getString(ProjectConstants.USER_LOGIN, "");
        }
        dialogItem = Parcels.unwrap(parcelable);
        initToolbar(title);
        initRecyclerView();
        initMessageEditor();
        createSendMessageTaskFragment();
        createChatDbFragment();
        getMessageItems();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable parcelable = Parcels.wrap(dialogItem);
        outState.putParcelable(ProjectConstants.DIALOG_ITEM_INTENT, parcelable);
        outState.putString(ProjectConstants.DIALOG_TITLE, tvTitle.getText().toString());
        outState.putString(ProjectConstants.USER_LOGIN, login);
    }

    private void initToolbar(String tittle){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_dialog_title_toolbar);
        tvTitle.setText(tittle);
    }

    private void initRecyclerView(){
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recycler_view_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        adapter = new MessageAdapter(clickRecyclerMessageItem, login);
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
    }

    private void initMessageEditor(){
        messageEditor = (MessageEditor) findViewById(R.id.message_editor);
        messageEditor.setOnClickListenerSend(clickSend);
    }

    private void createSendMessageTaskFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        sendMessageTaskFragment = (SendMessageTaskFragment) supportFragmentManager.findFragmentByTag(SendMessageTaskFragment.SEND_MESSAGE_TASK_FRAGMENT_TAG);
        if (sendMessageTaskFragment == null) {
            sendMessageTaskFragment = new SendMessageTaskFragment();
            supportFragmentManager.beginTransaction()
                    .add(sendMessageTaskFragment, SendMessageTaskFragment.SEND_MESSAGE_TASK_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void createChatDbFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        chatDbFragment = (ChatDbFragment) supportFragmentManager.findFragmentByTag(ChatDbFragment.CHAT_DB_FRAGMENT_TAG);
        if (chatDbFragment == null) {
            chatDbFragment = new ChatDbFragment();
            supportFragmentManager.beginTransaction()
                    .add(chatDbFragment, ChatDbFragment.CHAT_DB_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void addMessageItem(MessageItem messageItem) {
        DateHelper dateHelper = new DateHelper();
        messageItem.setTime(dateHelper.getCurrentTime());
        messageItem.setDate(dateHelper.getCurrentDate());
        messageItem.setDialogItem(dialogItem);
        ChatDbHelper helper = new ChatDbHelper(chatDbFragment);
        helper.addItem(messageItem);
    }

    private void getMessageItems(){
        ChatDbHelper helper = new ChatDbHelper(chatDbFragment);
        helper.getMessageItems(dialogItem);
    }

    private View.OnClickListener clickSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String messageText = messageEditor.getText();
            MessageItem messageItem = new MessageItem();
            messageItem.setMessageText(messageText);
            messageItem.setIdAuthor(login);
            addMessageItem(messageItem);
        }
    };

    private OnItemClickListener clickRecyclerMessageItem = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Toast.makeText(MessagesActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void itemAdded(BaseModel item) {
        MessageItem messageItem = (MessageItem) item;
        if (messageItem.getIdAuthor().equals(login))
            sendMessageTaskFragment.startSend(messageItem);
        if (((MessageAdapter) adapter).getData() != null) {
            ((MessageAdapter) adapter).addMessage(messageItem);
            recyclerViewMessage.scrollToPosition(0);
        }
    }

    @Override
    public void itemsReceived(List<? extends BaseModel> items) {
        List<MessageItem> messageItems = new LinkedList<>();
        for (BaseModel item: items){
            if (MessageItem.class.isInstance(item))
                messageItems.add((MessageItem) item);
            else
                throw new ArrayStoreException("Assignment to an array element of an incompatible type: " + item.toString());
        }
        ((MessageAdapter)adapter).setItems(messageItems);
    }

    @Override
    public void itemDeleted(BaseModel item) {
        //TODO
    }

    @Override
    public void messageSent(Boolean success) {
        if (success){
            Toast.makeText(this, "messageSent", Toast.LENGTH_SHORT).show();
        } else {
            //TODO
        }
    }

    @Override
    public Loader<MessageItem> onCreateLoader(int id, Bundle args) {
        return new MessageLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<MessageItem> loader, MessageItem data) {
        if (data != null){
            addMessageItem(data);
        }
        getSupportLoaderManager().destroyLoader(ID_MESSAGE_LOADER);
    }

    @Override
    public void onLoaderReset(Loader<MessageItem> loader) {
        //empty
    }
}
