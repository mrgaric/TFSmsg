package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.MessagesAdapter;
import com.igordubrovin.tfsmsg.customView.EmojiconEditTextClearFocus;
import com.igordubrovin.tfsmsg.utils.MessageItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;

public class MessagesActivity extends AppCompatActivity {

    private EmojiconEditTextClearFocus emojEditTextMessage;
    private ImageView emojiconImage;
    private ImageView clearImage;
    private ImageView sendImage;
    private EmojIconActions emojIconActions;
    private View root;

    private Toolbar toolbar;

    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;

    private List<MessageItem> messageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        if (savedInstanceState == null)
            messageItems = new LinkedList<>();
        else{
            List<MessageItem> savedData = savedInstanceState.getParcelableArrayList("asd");
            if (savedData != null)
                messageItems = new LinkedList<>(savedData);
        }

        initToolbar(getIntent().getStringExtra("titleDialog"));
        initRecyclerView();
        initEmojiconView();
        initImageView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<MessageItem> savedData = new ArrayList<>(messageItems);
        outState.putParcelableArrayList("asd", (ArrayList<MessageItem>) savedData);
    }

    //init view

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
        adapter = new MessagesAdapter(messageItems, clickRecyclerMessageItem);
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
    }

    private void initEmojiconView(){
        emojEditTextMessage = (EmojiconEditTextClearFocus) findViewById(R.id.edit_text_message);
        emojEditTextMessage.setOnTextEmptyListener(emptyEditTextListener);
        emojiconImage = (ImageView) findViewById(R.id.image_view_emojicon);
        root = findViewById(R.id.root_layout_emojicon_message);
        emojIconActions = new EmojIconActions(this, root, emojEditTextMessage, emojiconImage);
        emojIconActions.ShowEmojIcon();
        emojIconActions.setIconsIds(R.drawable.ic_keyboard, R.drawable.ic_insert_emoticon);
    }

    private void initImageView(){
        sendImage = (ImageView) findViewById(R.id.image_view_send);
        clearImage = (ImageView) findViewById(R.id.image_view_clear);
        sendImage.setOnClickListener(clickSendImage);
        clearImage.setOnClickListener(clickClearImage);
    }

    // listeners view

    private View.OnClickListener clickSendImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = emojEditTextMessage.getText().toString();
            updateAdapter(message);
            emojEditTextMessage.setText("");
        }
    };

    private View.OnClickListener clickClearImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            emojEditTextMessage.setText("");
        }
    };

    private EmojiconEditTextClearFocus.OnTextEmptyListener emptyEditTextListener = new EmojiconEditTextClearFocus.OnTextEmptyListener() {
        @Override
        public void textIsNotEmpty() {
            clearImage.setVisibility(View.VISIBLE);
            sendImage.setClickable(true);
        }

        @Override
        public void textIsEmpty() {
            clearImage.setVisibility(View.INVISIBLE);
            sendImage.setClickable(false);
        }
    };

    private OnItemClickListener clickRecyclerMessageItem = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(MessagesActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
        }
    };

    //other

    private void receiveMessage(){
        String message = "";
        updateAdapter(message);
    }

    private void updateAdapter(String message){
        ((LinkedList<MessageItem>)messageItems).addFirst(new MessageItem(message));
        adapter.notifyDataSetChanged();
    }
}
