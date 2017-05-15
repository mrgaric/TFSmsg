package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IDialogsView;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Игорь on 15.05.2017.
 */

public interface IDialogsPresenter extends MvpPresenter<IDialogsView> {
    void loadDialogsList();
    void addDialogItem(BaseModel item);
}
