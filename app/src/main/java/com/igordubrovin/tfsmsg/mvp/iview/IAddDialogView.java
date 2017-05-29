package com.igordubrovin.tfsmsg.mvp.iview;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.igordubrovin.tfsmsg.utils.DialogItem;

/**
 * Created by Ксения on 30.05.2017.
 */

public interface IAddDialogView extends MvpView {
    void dialogAdded(DialogItem dialogItem);
    void showError(Exception e);
}
