package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.widgets.ItemMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Игорь on 03.04.2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageItem> dataMessage;
    private OnItemClickListener clickListener;
    private String login;

    public MessageAdapter(OnItemClickListener clickListener, String login){
        this.clickListener = clickListener;
        this.login = login;
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
            ((MessageAdapter.ViewHolder)holder).itemMessage.setType(ItemMessage.TYPE_OUT);

        } else {
            ((MessageAdapter.ViewHolder)holder).itemMessage.setTvSender((item).getIdAuthor());
            ((MessageAdapter.ViewHolder)holder).itemMessage.setType(ItemMessage.TYPE_IN);
        }
        ((MessageAdapter.ViewHolder)holder).itemMessage.setTextMessage(item.getMessageText());
        ((MessageAdapter.ViewHolder)holder).itemMessage.setTime(item.getTime());
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
        dataMessage = messageItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemMessage itemMessage;

        public ViewHolder(View itemView, OnItemClickListener clickListener){
            super(itemView);
            itemMessage = (ItemMessage) itemView.findViewById(R.id.item_message);
            setClickListener(clickListener);
        }

        protected void setClickListener(final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

}