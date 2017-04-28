package com.igordubrovin.tfsmsg.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.activities.MessagesActivity;
import com.igordubrovin.tfsmsg.adapters.DialogsAdapter;
import com.igordubrovin.tfsmsg.db.ChatDbHelper;
import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by Игорь on 26.03.2017.
 */

public class DialogsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        initRecyclerView(view);
        getDialogItemsDb();
        return view;
    }

    private void initRecyclerView(final View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DialogsAdapter(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), MessagesActivity.class);
                intent.putExtra(ProjectConstants.DIALOG_ITEM_INTENT, Parcels.wrap(((DialogsAdapter)adapter).getItem(position)));
                intent.putExtra(ProjectConstants.DIALOG_TITLE, ((DialogsAdapter)adapter).getItem(position).getTitle());
                Pair<View, String> pair = new Pair<>(v.findViewById(R.id.tv_dialog_title), getString(R.string.transition_name_title_dialog));
                @SuppressWarnings("unchecked")
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair);
                ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void clickFAB(){
        int itemCount = adapter.getItemCount();
        DialogItem dialogItem = new DialogItem("Title is " + itemCount, "Description is " + itemCount);
        addDialogItem(dialogItem);
    }

    private void addDialogItem(final DialogItem dialogItem) {
        ChatDbHelper helper = new ChatDbHelper();
        helper.addItem(dialogItem, new Transaction.Success() {
            @Override
            public void onSuccess(Transaction transaction) {
                ((DialogsAdapter)adapter).addDialog(dialogItem);
            }
        });
    }

    private void getDialogItemsDb() {
        ChatDbHelper helper = new ChatDbHelper();
        helper.getDialogItemsDb(new QueryTransaction.QueryResultListCallback<DialogItem>() {
            @Override
            public void onListQueryResult(QueryTransaction transaction, @NonNull List<DialogItem> tResult) {
                ((DialogsAdapter)adapter).setItems(tResult);
            }
        });
    }
}
