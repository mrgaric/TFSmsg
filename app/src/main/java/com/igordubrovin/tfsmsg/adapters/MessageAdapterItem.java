package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.utils.MessageIncomingItem;
import com.igordubrovin.tfsmsg.utils.MessageItem;
import com.igordubrovin.tfsmsg.widgets.ItemMessage;

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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incoming_message_new, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageItem item = dataMessage.get(position);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (MessageIncomingItem.class.isInstance(item)) {
            ((MessageAdapterItem.ViewHolder)holder).itemMessage.setSender(((MessageIncomingItem)item).getSender());
            ((MessageAdapterItem.ViewHolder)holder).itemMessage.setType(ItemMessage.TYPE_IN);
            params.gravity = Gravity.LEFT;
            ((MessageAdapterItem.ViewHolder)holder).itemMessage.setLayoutParams(params);

        } else if (MessageItem.class.isInstance(item)) {
            ((MessageAdapterItem.ViewHolder)holder).itemMessage.setType(ItemMessage.TYPE_OUT);
            params.gravity = Gravity.RIGHT;
            ((MessageAdapterItem.ViewHolder)holder).itemMessage.setLayoutParams(params);
        } else
            throw new IllegalArgumentException("Unknown element" + position);

        ((MessageAdapterItem.ViewHolder)holder).itemMessage.setTextMessage(item.getMessageText());
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