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

import java.util.ArrayList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MessageActivity extends AppCompatActivity implements OnItemClickListener{

    private EmojiconEditText emojEditTextMessage;
    private ImageView emojiconImage;
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

        root = findViewById(R.id.root_constrain_layout_message);
        emojiconImage = (ImageView) findViewById(R.id.image_view_emojicon);
        sendImage = (ImageView) findViewById(R.id.image_view_send);
        emojEditTextMessage = (EmojiconEditText) findViewById(R.id.edit_text_message);
        emojIconActions = new EmojIconActions(this, root, emojEditTextMessage, emojiconImage);
        emojIconActions.ShowEmojIcon();
        emojIconActions.setIconsIds(R.drawable.ic_keyboard_black, R.drawable.ic_insert_emoticon_black);
        sendImage.setOnClickListener(clickSendImage);
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

    private void initRecyclerView(){
        messageList = new ArrayList<>();
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recycler_view_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MessageAdapter(messageList, this);
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
    }

    private View.OnClickListener clickSendImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = emojEditTextMessage.getText().toString();
            if (!message.equals("")) {
                messageList.add(new MessageItem(message));
                adapter.notifyDataSetChanged();
                recyclerViewMessage.scrollToPosition(adapter.getItemCount()-1);
            }
        }
    };

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

    @Override
    public void onItemClick(int position) {
        Toast.makeText(MessageActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
    }
}
