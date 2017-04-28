package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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
import com.igordubrovin.tfsmsg.fragments.SendMessageTaskFragment;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.loaders.MessageLoader;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.igordubrovin.tfsmsg.widgets.MessageEditor;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.parceler.Parcels;

import java.util.LinkedList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity
        implements SendMessageTaskFragment.MessageSentListener,
        LoaderManager.LoaderCallbacks<MessageItem>{

    private MessageEditor messageEditor;
    private Toolbar toolbar;
    private TextView tvTitle;
    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;
    private SendMessageTaskFragment sendMessageTaskFragment;
    private Loader messageLoader;
    private DialogItem dialogItem;

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
        } else {
            parcelable = savedInstanceState.getParcelable(ProjectConstants.DIALOG_ITEM_INTENT);
            title = savedInstanceState.getString(ProjectConstants.DIALOG_TITLE, "Title");
        }
        dialogItem = Parcels.unwrap(parcelable);
        messageLoader = getSupportLoaderManager().initLoader(ID_MESSAGE_LOADER, null, this);
        initToolbar(title);
        initRecyclerView();
        initMessageEditor();
        initFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable parcelable = Parcels.wrap(dialogItem);
        outState.putParcelable(ProjectConstants.DIALOG_ITEM_INTENT, parcelable);
        outState.putString(ProjectConstants.DIALOG_TITLE, tvTitle.getText().toString());
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
        adapter = new MessageAdapter(clickRecyclerMessageItem);
        getMessageItems();
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
            addMessageItem(message);
        }
    };

    private OnItemClickListener clickRecyclerMessageItem = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Toast.makeText(MessagesActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
        }
    };

    private void addMessageItem(final MessageItem messageItem) {
        messageItem.setDialogItem(dialogItem);
        ChatDbHelper helper = new ChatDbHelper();
        helper.addItem(messageItem, new Transaction.Success() {
            @Override
            public void onSuccess(Transaction transaction) {
                sendMessageTaskFragment.startSend(messageItem);
                ((MessageAdapter)adapter).addMessage(messageItem);
                recyclerViewMessage.scrollToPosition(0);
            }
        });
    }

    private void getMessageItems(){
        ChatDbHelper helper = new ChatDbHelper();
        helper.getMessageItems(dialogItem, new QueryTransaction.QueryResultListCallback<MessageItem>() {
            @Override
            public void onListQueryResult(QueryTransaction transaction, @NonNull List<MessageItem> tResult) {
                List<MessageItem> messageItems = new LinkedList<>(tResult);
                ((MessageAdapter)adapter).setItems(messageItems);
            }
        });
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
    }

    @Override
    public void onLoaderReset(Loader<MessageItem> loader) {

    }
}
