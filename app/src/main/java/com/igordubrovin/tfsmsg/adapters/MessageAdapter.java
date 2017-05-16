package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.widgets.MessageView;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Игорь on 03.04.2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageItem> dataMessage;
    private OnItemClickListener clickListener;
    private String login;

    @Inject
    public MessageAdapter(String login){
        this.login = login;
    }
    //сравнение дат текущего элемента и предидущего
    private boolean checkHeader(int position){
        return (dataMessage.size() == 1)
                || (position == dataMessage.size() - 1)
                || (!dataMessage.get(position).getDate().equals(dataMessage.get(position + 1).getDate()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageItem item = dataMessage.get(position);
        if (item.getIdAuthor().equals(login)) {
            ((MessageAdapter.ViewHolder) holder).itemMessage.setType(MessageView.TYPE_OUT);
        } else {
            ((MessageAdapter.ViewHolder) holder).itemMessage.setTvSender((item).getIdAuthor());
            ((MessageAdapter.ViewHolder) holder).itemMessage.setType(MessageView.TYPE_IN);
        }
        if (checkHeader(position)){
            ((MessageAdapter.ViewHolder) holder).itemMessage.setVisibilityTvDate(MessageView.VISIBLE_DATE);
            ((MessageAdapter.ViewHolder) holder).itemMessage.setTvDate(item.getDate());
        } else {
            ((MessageAdapter.ViewHolder) holder).itemMessage.setVisibilityTvDate(MessageView.GONE_DATE);
        }
        ((MessageAdapter.ViewHolder) holder).itemMessage.setTextMessage(item.getMessageText());
        ((MessageAdapter.ViewHolder) holder).itemMessage.setTvTime(item.getTime());
    }

    @Override
    public int getItemCount() {
        return dataMessage == null ? 0 : dataMessage.size();
    }

    public List<MessageItem> getData(){
        return dataMessage;
    }

    public void addMessage(MessageItem messageItem) {
        ((LinkedList<MessageItem>)dataMessage).addFirst(messageItem);
        notifyItemInserted(0);
    }

    public void setItems(List<MessageItem> messageItems) {
        dataMessage = new LinkedList<>(messageItems);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_message)
        MessageView itemMessage;

        public ViewHolder(View itemView, OnItemClickListener clickListener){
            super(itemView);
            ButterKnife.bind(this, itemView);
            setClickListener(clickListener);
        }

        protected void setClickListener(final OnItemClickListener listener){
            itemView.setOnClickListener(v -> {
                if (listener != null)
                    listener.onItemClick(v, getAdapterPosition());
            });
        }
    }
}