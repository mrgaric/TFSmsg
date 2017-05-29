package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IAddDialogView;
import com.igordubrovin.tfsmsg.utils.DialogItem;

/**
 * Created by Ксения on 30.05.2017.
 */

public interface IAddDialogPresenter extends MvpPresenter<IAddDialogView> {
    void addDialog(DialogItem dialogItem);
}
