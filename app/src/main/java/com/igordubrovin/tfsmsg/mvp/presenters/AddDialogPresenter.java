package com.igordubrovin.tfsmsg.mvp.presenters;

import android.content.Intent;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.firebase.OnTransactionComplete;
import com.igordubrovin.tfsmsg.firebase.dialog.DialogRepository;
import com.igordubrovin.tfsmsg.mvp.ipresenter.IAddDialogPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IAddDialogView;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.utils.ProjectConstants;

import org.parceler.Parcels;

import javax.inject.Inject;

/**
 * Created by Ксения on 30.05.2017.
 */

public class AddDialogPresenter extends MvpBasePresenter<IAddDialogView>
        implements IAddDialogPresenter {

    private final DialogRepository dialogRepository;
    private Boolean success;
    private DialogItem dialogItem;
    private Exception e;
    @Inject
    AddDialogPresenter(DialogRepository dialogRepository){
        this.dialogRepository = dialogRepository;
    }

    @Override
    public void attachView(IAddDialogView view) {
        super.attachView(view);
        if (success != null){
            if (success) {
                getView().dialogAdded(dialogItem);
                success = null;
                dialogItem = null;
            } else {
                getView().showError(e);
                success = null;
                e = null;
            }
        }
    }

    @Override
    public void addDialog(DialogItem dialogItem) {
        dialogRepository.addDialog(dialogItem, new OnTransactionComplete<Void>() {
            @Override
            public void onCommit(Void result) {
                dialogAdded(dialogItem);
            }

            @Override
            public void onAbort(Exception e) {
                showError(e);
            }
        });
    }

    private void dialogAdded(DialogItem dialogItem){
        if (isViewAttached()) {
            getView().dialogAdded(dialogItem);
        } else {
            success = true;
            this.dialogItem = dialogItem;
        }
    }

    private void showError(Exception e){
        if (isViewAttached()) {
            getView().showError(e);
        } else {
            success = false;
            this.e = e;
        }
    }
}
