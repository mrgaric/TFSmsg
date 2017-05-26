package com.igordubrovin.tfsmsg.mvp.iview;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Игорь on 15.05.2017.
 */

public interface IDialogsView extends MvpView {
    void showDialogs(List<DialogItem> dialogItems);
    void showAddedItem(BaseModel item);
}
