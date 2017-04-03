package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.utils.MessageIncomingItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;

import java.util.List;

/**
 * Created by Игорь on 03.04.2017.
 */

public class MessageAdapterItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageItem> dataMessage;
    private OnItemClickListener clickListener;

    public MessageAdapterItem(List<MessageItem> dataMessage, OnItemClickListener clickListener){
        this.dataMessage = dataMessage;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outgoing_message, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (MessagesAdapter.ViewHolderOutgoingMessage.class.isInstance(holder)){
            MessageItem item = dataMessage.get(position);
            ((MessagesAdapter.ViewHolderOutgoingMessage)holder).message.setText(item.getMessageText());
        }
        else {
            MessageIncomingItem item = (MessageIncomingItem) dataMessage.get(position);
            ((MessagesAdapter.ViewHolderIncomingMessage)holder).message.setText(item.getMessageText());
            ((MessagesAdapter.ViewHolderIncomingMessage)holder).sender.setText(item.getSender());
        }
    }

    @Override
    public int getItemCount() {
        return dataMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView, OnItemClickListener clickListener){
            super(itemView);
        }

        protected void setClickListener(final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}