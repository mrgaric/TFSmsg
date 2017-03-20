package com.igordubrovin.tfsmsg;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class MessageActivity extends AppCompatActivity implements OnItemClickListener{

    private EmojiconTextView emojiconTextView;
    private EmojiconEditText emojiconEditText;
    private ImageView imageView;
    private ImageView imageViewSend;
    private EmojIconActions emojIconActions;
    private View root;

    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initRecyclerView();

        root = findViewById(R.id.root);
        imageView = (ImageView) findViewById(R.id.imageView2);
        imageViewSend = (ImageView) findViewById(R.id.imageView3);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojiconEditText);
        emojiconTextView = (EmojiconTextView) findViewById(R.id.emojiconTextView);
        emojIconActions = new EmojIconActions(this, root, emojiconEditText, imageView);
        emojIconActions.ShowEmojIcon();
        emojIconActions.setIconsIds(R.drawable.ic_keyboard_black, R.drawable.ic_insert_emoticon_black);
        emojIconActions.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.d("myLog", "Keyboard opened!");
            }

            @Override
            public void onKeyboardClose() {
                Log.d("myLog", "Keyboard closed");
            }
        });
        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = emojiconEditText.getText().toString();
                emojiconTextView.setText(text);
            }
        });
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
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recycler_view_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        adapter = new MessageAdapter(createDataSet(), this);
        recyclerViewMessage.setLayoutManager(layoutManager);
        recyclerViewMessage.setAdapter(adapter);
    }

    private List<MessageItem> createDataSet() {
        List<MessageItem> list = new ArrayList<>();
        /*list.add(new MessageItem("test"));
        list.add(new MessageIncomingItem("Test", "sender"));
        list.add(new MessageItem("test"));
        list.add(new MessageItem("test"));
        list.add(new MessageIncomingItem("Test", "sender"));
        list.add(new MessageIncomingItem("Test", "sender"));*/
        return list;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(MessageActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
    }
}
