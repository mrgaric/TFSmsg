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
import com.igordubrovin.tfsmsg.interfaces.InjectFragment;
import com.igordubrovin.tfsmsg.interfaces.OnItemClickListener;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IDialogsPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IDialogsView;
import com.igordubrovin.tfsmsg.mvp.presenters.DialogsPresenter;
import com.igordubrovin.tfsmsg.utils.DateHelper;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InjectFragment)
            ((InjectFragment)context).injectDialogsFragment(this);
        else
            throw new IllegalStateException("You should provide InjectFragment");
    }

    @Override
    public IDialogsPresenter createPresenter() {
        return dialogsPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(final View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setItemClickListener(itemClickListener);
        getDialogItemsDb();
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    OnItemClickListener itemClickListener = new OnItemClickListener() {
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
    };

    public void getDialogItemsDb() {
    //    ChatDbHelper helper = new ChatDbHelper(this);
    //    helper.getDialogItemsDb();
    }

    public void clickFAB(){
        int itemCount = adapter.getItemCount();
        DateHelper dateHelper = new DateHelper();
        DialogItem dialogItem = new DialogItem();
        dialogItem.setTitle("Title is " + itemCount);
        dialogItem.setDesc("Description is " + itemCount);
        dialogItem.setTime(dateHelper.getCurrentTime());
        dialogItem.setDate(dateHelper.getCurrentDate());
        addDialogItem(dialogItem);
    }

    private void addDialogItem(final DialogItem dialogItem) {
     //   ChatDbHelper helper = new ChatDbHelper(this);
      //  helper.saveItem(dialogItem);
    }

    @Override
    public void showDialogs(List<DialogItem> dialogItems) {

    }

    /*@Override
    public void itemAdded(BaseModel item) {
        ((DialogsAdapter)adapter).addDialog((DialogItem) item);
    }

    @Override
    public void itemsReceived(List<? extends BaseModel> items) {
        List<DialogItem> dialogItems = new ArrayList<>();
        for (BaseModel item: items){
            if (DialogItem.class.isInstance(item))
                dialogItems.add((DialogItem) item);
            else
                throw new ArrayStoreException("Assignment to an array element of an incompatible type: " + item.toString());
        }
        ((DialogsAdapter)adapter).setItems(dialogItems);
    }

    @Override
    public void itemDeleted(BaseModel item) {
        //TODO
    }*/
}
