package com.igordubrovin.tfsmsg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.widgets.ItemMessage;

/**
 * Created by Игорь on 26.03.2017.
 */

public class DialogsFragment extends Fragment {
    ItemMessage itemMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_incoming_message_new, container, false);
        return view;
    }

    /*private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<DialogItem> dialogItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dialogItems = new ArrayList<>();
        createDataset(dialogItems);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DialogsAdapter(dialogItems, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MessagesActivity.class);
                intent.putExtra(ProjectConstants.DIALOG_TITLE, ((DialogsAdapter)adapter).getItem(position).getTitle());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void createDataset(List<DialogItem> list) {
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
    }*/
}
