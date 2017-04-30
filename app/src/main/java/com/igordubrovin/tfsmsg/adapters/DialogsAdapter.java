package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.R;

import java.util.List;

public class DialogsAdapter extends RecyclerView.Adapter<DialogsAdapter.ViewHolder> {

    private List<DialogItem> dataset;
    private OnItemClickListener clickListener;

    public DialogsAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public DialogsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_dialog, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(dataset.get(position).getTitle());
        holder.tvDesc.setText(dataset.get(position).getDesc());
        String lastMessage = dataset.get(position).getLastMessage();
        if (lastMessage != null && !lastMessage.equals("")){
            holder.tvLastMessage.setText(lastMessage);
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    public DialogItem getItem(int position){
        return dataset.get(position);
    }

    public void addDialog(DialogItem dialogItem) {
        dataset.add(dialogItem);
        notifyItemInserted(dataset.size());
    }

    public void setItems(List<DialogItem> dialogItems) {
        dataset = dialogItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvDesc;
        public TextView tvLastMessage;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
            tvDesc = (TextView) view.findViewById(R.id.tv_dialog_desc);
            tvLastMessage = (TextView) view.findViewById(R.id.tv_dialog_last_message);
            setListener(listener);
        }

        private void setListener(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }
}