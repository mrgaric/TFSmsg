package com.igordubrovin.tfsmsg;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements OnItemClickListener{

    private RecyclerView recyclerViewMessage;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initRecyclerView();
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
        list.add(new MessageItem("test"));
        list.add(new MessageIncomingItem("Test", "sender"));
        list.add(new MessageItem("test"));
        list.add(new MessageItem("test"));
        list.add(new MessageIncomingItem("Test", "sender"));
        list.add(new MessageIncomingItem("Test", "sender"));
        return list;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(MessageActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
    }
}
