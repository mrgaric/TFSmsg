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
    List<DialogItem> dialogItems;
    DBFlowHelper dbFlowHelper;

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
    }

    @Override
    public void loadDialogsList() {
        dbFlowHelper.getDialogItemsDb(this);
    }

    @Override
    public void itemAdded(BaseModel item) {

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
