package com.igordubrovin.tfsmsg.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogsAdapter extends RecyclerView.Adapter<DialogsAdapter.ViewHolder> {

    private List<DialogItem> dataset;
    private OnItemClickListener clickListener;

    @Override
    public DialogsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_dialog, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(dataset.get(position).getTitle());
        holder.tvDesc.setText(dataset.get(position).getDesc());
        //String lastMessage = dataset.get(position).getLastMessage();
        /*if (lastMessage != null && !lastMessage.equals("")){
            holder.tvLastMessage.setText(lastMessage);
        }*/
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    public DialogItem getItem(int position){
        return dataset.get(position);
    }

    public void addDialog(DialogItem dialogItem) {
        ((LinkedList<DialogItem>)dataset).addFirst(dialogItem);
        notifyItemInserted(0);
    }

    public void setItems(List<DialogItem> dialogItems) {
        dataset = new LinkedList<>(dialogItems);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        this.clickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_dialog_title)
        TextView tvTitle;
        @BindView(R.id.tv_dialog_desc)
        TextView tvDesc;
        @BindView(R.id.tv_dialog_last_message)
        TextView tvLastMessage;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            setClickListener(listener);
        }

        private void setClickListener(final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }
}