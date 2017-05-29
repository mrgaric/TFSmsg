package com.igordubrovin.tfsmsg.mvp.presenters;

import android.support.annotation.VisibleForTesting;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.firebase.DialogItemValueListener;
import com.igordubrovin.tfsmsg.firebase.dialog.DialogRepository;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IDialogsPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IDialogsView;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Игорь on 15.05.2017.
 */

public class DialogsPresenter extends MvpBasePresenter<IDialogsView>
        implements IDialogsPresenter{
    @VisibleForTesting
    public List<DialogItem> dialogItems;
    private BaseModel item;
    private DialogRepository dialogRepository;

    @Inject
    public DialogsPresenter(DialogRepository dialogRepository){
        this.dialogRepository = dialogRepository;
    }

    @Override
    public void attachView(IDialogsView view) {
        super.attachView(view);
        if (dialogItems != null) {
            view.showDialogs(dialogItems);
            dialogItems = null;
        }
        if (item != null){
            getView().showAddedItem(item);
            item = null;
        }
    }

    @Override
    public void loadDialogsList() {
        dialogRepository.getDialogs(new DialogItemValueListener<DialogItem>() {
            @Override
            public void onValue(List<DialogItem> items) {
                if (isViewAttached())
                    getView().showDialogs(items);
                else
                    dialogItems = items;
            }
        });
    }

    @Override
    public void addDialogItem(DialogItem item) {

    }

    /*@Override
    public void addDialogItem(BaseModel item) {
      //  dbFlowHelper.saveItem(item, this);
    }*/

   /* @Override
    public void itemAdded(BaseModel item) {
        if (isViewAttached())
            getView().showAddedItem(item);
        else
            this.item = item;
    }*/

    /*@Override
    public void itemsReceived(List<? extends BaseModel> items) {
        if (isViewAttached())
            getView().showDialogs((List<DialogItem>) items);
        else
            dialogItems = (List<DialogItem>) items;
    }

    @Override
    public void itemDeleted(BaseModel item) {

    }*/
}
