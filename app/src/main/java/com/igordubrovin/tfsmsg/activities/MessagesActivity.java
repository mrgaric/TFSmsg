package com.igordubrovin.tfsmsg.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.adapters.MessagesAdapter;
import com.igordubrovin.tfsmsg.customView.EditText;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.utils.MessageIncomingItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private ImageView clearImage;
    private ImageView sendImage;

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

    private void initMessageEditor(){
        editTextMessage = (EditText) findViewById(R.id.edit_text_message);
        editTextMessage.setOnTextEmptyListener(emptyEditTextListener);
        sendImage = (ImageView) findViewById(R.id.image_view_send);
        sendImage.setOnClickListener(clickSendImage);
        sendImage.setClickable(false);
        clearImage = (ImageView) findViewById(R.id.image_view_clear);
        clearImage.setOnClickListener(clickClearImage);
    }

    // listeners view

    private View.OnClickListener clickSendImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = editTextMessage.getText().toString();
            ((LinkedList<MessageItem>)messageItems).addFirst(new MessageItem(message));
            adapter.notifyDataSetChanged();
            recyclerViewMessage.scrollToPosition(0);
            editTextMessage.setText("");
            receiveMessage();
        }
    };

    private View.OnClickListener clickClearImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editTextMessage.setText("");
        }
    };

    private EditText.OnTextEmptyListener emptyEditTextListener = new EditText.OnTextEmptyListener() {
        @Override
        public void textIsNotEmpty() {
            if (clearImage.getVisibility() == View.INVISIBLE && !sendImage.isClickable()) {
                sendImage.setClickable(true);
                sendImage.setImageResource(R.drawable.ic_send_color_accent);
                clearImage.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clear_image_show);
                clearImage.startAnimation(anim);
            }
        }

        @Override
        public void textIsEmpty() {
            sendImage.setClickable(false);
            sendImage.setImageResource(R.drawable.ic_send_color_gray);
            clearImage.setVisibility(View.INVISIBLE);
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clear_image_hide);
            clearImage.startAnimation(anim);
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
        String sender = "test test test test test test test test test";
        String message = "Test Test Test Test Test Test Test Test Test Test";
        ((LinkedList<MessageItem>)messageItems).addFirst(new MessageIncomingItem(message, sender));
        adapter.notifyDataSetChanged();
    }

}
