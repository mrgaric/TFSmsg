package com.igordubrovin.tfsmsg;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;

public class MessageActivity extends AppCompatActivity implements OnItemClickListener{

    private EmojiconEditTextClearFocus emojEditTextMessage;
    private ImageView emojiconImage;
    private ImageView clearImage;
    private ImageView sendImage;
    private EmojIconActions emojIconActions;
    private View root;

    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;

    private List<MessageItem> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initRecyclerView();
        initEmojiconView();

        sendImage = (ImageView) findViewById(R.id.image_view_send);
        clearImage = (ImageView) findViewById(R.id.image_view_clear);

        sendImage.setOnClickListener(clickSendImage);
        clearImage.setOnClickListener(clickClearImage);

    }

    private void initRecyclerView(){
        messageList = new LinkedList<>();
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recycler_view_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        adapter = new MessageAdapter(messageList, this);
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
    }

    private void initEmojiconView(){
        emojEditTextMessage = (EmojiconEditTextClearFocus) findViewById(R.id.edit_text_message);
        emojEditTextMessage.setOnTextEmptyListener(notEmptyEditText);
        emojiconImage = (ImageView) findViewById(R.id.image_view_emojicon);
        root = findViewById(R.id.root_constrain_layout_message);
        emojIconActions = new EmojIconActions(this, root, emojEditTextMessage, emojiconImage);
        emojIconActions.ShowEmojIcon();
        emojIconActions.setIconsIds(R.drawable.ic_keyboard_black, R.drawable.ic_insert_emoticon_black);
    }

    private View.OnClickListener clickSendImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = emojEditTextMessage.getText().toString();
            ((LinkedList<MessageItem>)messageList).addFirst(new MessageItem(message));
            adapter.notifyDataSetChanged();
            emojEditTextMessage.setText("");
        }
    };

    private View.OnClickListener clickClearImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            emojEditTextMessage.setText("");
        }
    };

    EmojiconEditTextClearFocus.OnTextEmptyListener notEmptyEditText = new EmojiconEditTextClearFocus.OnTextEmptyListener() {
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

    @Override
    public void onItemClick(int position) {
        Toast.makeText(MessageActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  /*  private List<MessageItem> createDataSet() {
        List<MessageItem> list = new ArrayList<>();
        list.add(new MessageItem("test"));
        list.add(new MessageIncomingItem("Test", "sender"));
        list.add(new MessageItem("test"));
        list.add(new MessageItem("test"));
        list.add(new MessageIncomingItem("Test", "sender"));
        list.add(new MessageIncomingItem("Test", "sender"));
        return list;
    }*/
}
