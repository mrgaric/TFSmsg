package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.utils.MessageIncomingItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.widgets.ItemMessage;

import java.util.List;

/**
 * Created by Игорь on 03.04.2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageItem> dataMessage;
    private OnItemClickListener clickListener;

    public MessageAdapter(List<MessageItem> dataMessage, OnItemClickListener clickListener){
        this.dataMessage = dataMessage;
        this.clickListener = clickListener;
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
        if (MessageIncomingItem.class.isInstance(item)) {
            ((MessageAdapter.ViewHolder)holder).itemMessage.setSender(((MessageIncomingItem)item).getSender());
            ((MessageAdapter.ViewHolder)holder).itemMessage.setType(ItemMessage.TYPE_IN);

        } else if (MessageItem.class.isInstance(item)) {
            ((MessageAdapter.ViewHolder)holder).itemMessage.setType(ItemMessage.TYPE_OUT);
        } else
            throw new IllegalArgumentException("Unknown element" + position);

        ((MessageAdapter.ViewHolder)holder).itemMessage.setTextMessage(item.getMessageText());
    }

    @Override
    public int getItemCount() {
        return dataMessage.size();
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
                        listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}