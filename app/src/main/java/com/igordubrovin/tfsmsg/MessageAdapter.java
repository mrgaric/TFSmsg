package com.igordubrovin.tfsmsg;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Игорь on 18.03.2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageItem> dataSet;
    private OnItemClickListener clickListener;

    public MessageAdapter(List<MessageItem> dataSet, OnItemClickListener clickListener){
        this.dataSet = dataSet;
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) throws IllegalArgumentException {
        MessageItem item = dataSet.get(position);
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
            MessageItem item = dataSet.get(position);
            ((ViewHolderOutgoingMessage)holder).message.setText(item.getMessageText());
        }
        else {
            MessageIncomingItem item = (MessageIncomingItem) dataSet.get(position);
            ((ViewHolderIncomingMessage)holder).message.setText(item.getMessageText());
            ((ViewHolderIncomingMessage)holder).sender.setText(item.getSender());
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
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

        TextView message;

        public ViewHolderOutgoingMessage(View itemView, OnItemClickListener listener) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.text_view_outgoing_message);
            setClickListener(listener);
        }
    }

    public class ViewHolderIncomingMessage extends ViewHolder {

        TextView message;
        TextView sender;
        public ViewHolderIncomingMessage(View itemView, OnItemClickListener listener) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.text_view_incoming_message);
            sender = (TextView) itemView.findViewById(R.id.text_view_sender);
            setClickListener(listener);
        }
    }
}
