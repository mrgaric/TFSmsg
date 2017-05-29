package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.MessageAdapter;
import com.igordubrovin.tfsmsg.di.components.CommonComponent;
import com.igordubrovin.tfsmsg.di.components.MessageScreenComponent;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IMessagePresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IMessageView;
import com.igordubrovin.tfsmsg.mvp.presenters.MessagePresenter;
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.DateHelper;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.igordubrovin.tfsmsg.widgets.MessageEditor;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesActivity extends MvpActivity<IMessageView, IMessagePresenter>
        implements IMessageView{

    @BindView(R.id.message_editor)
    MessageEditor messageEditor;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_dialog_title_toolbar)
    TextView tvTitle;
    @BindView(R.id.recycler_view_message)
    RecyclerView recyclerViewMessage;
    @Inject
    MessagePresenter messagePresenter;
    @Inject
    MessageAdapter adapter;
    @Inject
    String login;
    private DialogItem dialogItem;
    private String dialogId;
    private CommonComponent commonComponent = App.plusCommonComponent();
    private MessageScreenComponent messageScreenComponent = App.plusMessageScreenComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        messageScreenComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        ButterKnife.bind(this);
        String title;
        Parcelable parcelable;
        if (savedInstanceState == null) {
            dialogId = getIntent().getStringExtra(ProjectConstants.DIALOG_ID);
            parcelable = getIntent().getParcelableExtra(ProjectConstants.DIALOG_ITEM_INTENT);
            title = getIntent().getStringExtra(ProjectConstants.DIALOG_TITLE);
        } else {
            dialogId = savedInstanceState.getString(ProjectConstants.DIALOG_ID);
            parcelable = savedInstanceState.getParcelable(ProjectConstants.DIALOG_ITEM_INTENT);
            title = savedInstanceState.getString(ProjectConstants.DIALOG_TITLE, "Title");
        }
        dialogItem = Parcels.unwrap(parcelable);
        initToolbar(title);
        initRecyclerView();
        initMessageEditor();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable parcelable = Parcels.wrap(dialogItem);
        outState.putParcelable(ProjectConstants.DIALOG_ITEM_INTENT, parcelable);
        outState.putString(ProjectConstants.DIALOG_TITLE, tvTitle.getText().toString());
        outState.putString(ProjectConstants.USER_LOGIN, login);
        outState.putString(ProjectConstants.DIALOG_ID, dialogId);
    }

    @NonNull
    @Override
    public IMessagePresenter createPresenter() {
        return messagePresenter;
    }

    private void initToolbar(String tittle){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        tvTitle.setText(tittle);
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        adapter.setOnItemClickListener((v, position) ->
                Toast.makeText(MessagesActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show());
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
        getMessageItems();
    }

    private void initMessageEditor(){
        messageEditor.setOnClickListenerSend(v -> {
            MessageItem messageItem = commonComponent.getMessageItem();
            DateHelper dateHelper = commonComponent.getDateHelper();
            messageItem.setTime(dateHelper.getCurrentTime());
            messageItem.setDate(dateHelper.getCurrentDate());
            messageItem.setMessageText(messageEditor.getText());
            messageItem.setIdAuthor(login);
            sendMessageItem(messageItem);
        });
    }

    private void getMessageItems(){
        getPresenter().getMessageItems(dialogId);
    }

    private void sendMessageItem(MessageItem messageItem){
        getPresenter().sendMessageItem(dialogId, messageItem);
    }

    private void updateLastMessageDialogItem(String sender, String lastMessage){
        dialogItem.setLastMessage(sender.concat(": ").concat(lastMessage));
        getPresenter().updateDialogItem(dialogItem);
    }

    @Override
    public void showMessages(List<MessageItem> messageItems) {
        adapter.setItems(messageItems);
    }

    @Override
    public void showAddedMessageItem(MessageItem item) {
        adapter.addMessage(item);
        if (item.getIdAuthor().equals(login))
            recyclerViewMessage.scrollToPosition(0);
        updateLastMessageDialogItem(item.getIdAuthor(), item.getMessageText());
    }

    @Override
    public void showErrorSendMessageItem() {
        //TODO
    }
}
