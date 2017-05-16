package com.igordubrovin.tfsmsg.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.igordubrovin.tfsmsg.R;
import com.igordubrovin.tfsmsg.activities.MessagesActivity;
import com.igordubrovin.tfsmsg.adapters.DialogsAdapter;
import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.di.components.CommonComponent;
import com.igordubrovin.tfsmsg.interfaces.InjectFragment;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IDialogsPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IDialogsView;
import com.igordubrovin.tfsmsg.mvp.presenters.DialogsPresenter;
import com.igordubrovin.tfsmsg.utils.App;
import com.igordubrovin.tfsmsg.utils.DateHelper;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Игорь on 26.03.2017.
 */

public class DialogsFragment extends MvpFragment<IDialogsView, IDialogsPresenter>
        implements IDialogsView{

    @BindView(R.id.recycler_view_dialogs)
    RecyclerView recyclerView;
    @Inject
    DialogsAdapter adapter;
    @Inject
    DialogsPresenter dialogsPresenter;
    private CommonComponent commonComponent  = App.plusCommonComponent();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InjectFragment)
            ((InjectFragment)context).injectDialogsFragment(this);
        else
            throw new IllegalStateException("You should provide InjectFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    @Override
    public IDialogsPresenter createPresenter() {
        return dialogsPresenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().loadDialogsList();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setItemClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent intent = new Intent(getContext(), MessagesActivity.class);
            intent.putExtra(ProjectConstants.DIALOG_ITEM_INTENT, Parcels.wrap(adapter.getItem(position)));
            intent.putExtra(ProjectConstants.DIALOG_TITLE, adapter.getItem(position).getTitle());
            Pair<View, String> pair = new Pair<>(v.findViewById(R.id.tv_dialog_title), getString(R.string.transition_name_title_dialog));
            @SuppressWarnings("unchecked")
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair);
            ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());
        }
    };

    public void clickFAB(){
        int itemCount = adapter.getItemCount();
        DateHelper dateHelper = commonComponent.getDateHelper();
        DialogItem dialogItem = commonComponent.getDialogItem();
        dialogItem.setTitle("Title is " + itemCount);
        dialogItem.setDesc("Description is " + itemCount);
        dialogItem.setTime(dateHelper.getCurrentTime());
        dialogItem.setDate(dateHelper.getCurrentDate());
        addDialogItem(dialogItem);
    }

    private void addDialogItem(final DialogItem dialogItem) {
        getPresenter().addDialogItem(dialogItem);
    }

    public void getDialogItemsDb(){
        getPresenter().loadDialogsList();
    }

    @Override
    public void showDialogs(List<DialogItem> dialogItems) {
        adapter.setItems(dialogItems);
    }

    @Override
    public void showAddedItem(BaseModel item) {
        adapter.addDialog((DialogItem) item);
        recyclerView.scrollToPosition(0);
    }
}
