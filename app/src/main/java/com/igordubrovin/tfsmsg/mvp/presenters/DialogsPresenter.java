package com.igordubrovin.tfsmsg.mvp.presenters;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.interfaces.ChatDbItemsListener;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IDialogsPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IDialogsView;
import com.igordubrovin.tfsmsg.utils.DBFlowHelper;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Игорь on 15.05.2017.
 */

public class DialogsPresenter extends MvpBasePresenter<IDialogsView>
        implements IDialogsPresenter,
        ChatDbItemsListener {
    private List<DialogItem> dialogItems;
    private BaseModel item;
    private DBFlowHelper dbFlowHelper;

    @Inject
    public DialogsPresenter(DBFlowHelper dbFlowHelper){
        this.dbFlowHelper = dbFlowHelper;
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
        dbFlowHelper.getDialogItemsDb(this);
    }

    @Override
    public void addDialogItem(BaseModel item) {
        dbFlowHelper.saveItem(item, this);
    }

    @Override
    public void itemAdded(BaseModel item) {
        if (isViewAttached())
            getView().showAddedItem(item);
        else
            this.item = item;
    }

    @Override
    public void itemsReceived(List<? extends BaseModel> items) {
        if (isViewAttached())
            getView().showDialogs((List<DialogItem>) items);
        else
            dialogItems = (List<DialogItem>) items;
    }

    @Override
    public void itemDeleted(BaseModel item) {

    }
}
