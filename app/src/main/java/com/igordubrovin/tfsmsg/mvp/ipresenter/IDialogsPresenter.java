package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.IDialogsView;

/**
 * Created by Игорь on 15.05.2017.
 */

public interface IDialogsPresenter extends MvpPresenter<IDialogsView> {
    void loadDialogsList();
}
