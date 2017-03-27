package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.utils.MessageIncomingItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.R;

import java.util.List;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by Игорь on 18.03.2017.
 */

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageItem> dataMessage;
    private OnItemClickListener clickListener;

    public MessagesAdapter(List<MessageItem> dataMessage, OnItemClickListener clickListener){
        this.dataMessage = dataMessage;
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) throws IllegalArgumentException {
        MessageItem item = dataMessage.get(position);
        if (MessageIncomingItem.class.isInstance(item)) return 0;
        if (MessageItem.class.isInstance(item)) return 1;
        throw new IllegalArgumentException("Unknown element");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outgoing_message, parent, false);
                viewHolder = new ViewHolderOutgoingMessage(view, clickListener);
                break;
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incoming_message, parent, false);
                viewHolder = new ViewHolderIncomingMessage(view, clickListener);
                break;
            default: viewHolder = null;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (ViewHolderOutgoingMessage.class.isInstance(holder)){
            MessageItem item = dataMessage.get(position);
            ((ViewHolderOutgoingMessage)holder).message.setText(item.getMessageText());
        }
        else {
            MessageIncomingItem item = (MessageIncomingItem) dataMessage.get(position);
            ((ViewHolderIncomingMessage)holder).message.setText(item.getMessageText());
            ((ViewHolderIncomingMessage)holder).sender.setText(item.getSender());
        }
    }

    @Override
    public int getItemCount() {
        return dataMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView){
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

    public class ViewHolderOutgoingMessage extends ViewHolder {

        EmojiconTextView message;

        public ViewHolderOutgoingMessage(View itemView, OnItemClickListener listener) {
            super(itemView);
            message = (EmojiconTextView) itemView.findViewById(R.id.text_view_outgoing_message);
            setClickListener(listener);
        }
    }

    public class ViewHolderIncomingMessage extends ViewHolder {

        EmojiconTextView message;
        TextView sender;
        public ViewHolderIncomingMessage(View itemView, OnItemClickListener listener) {
            super(itemView);
            message = (EmojiconTextView) itemView.findViewById(R.id.text_view_incoming_message);
            sender = (TextView) itemView.findViewById(R.id.text_view_sender);
            setClickListener(listener);
        }
    }
}
