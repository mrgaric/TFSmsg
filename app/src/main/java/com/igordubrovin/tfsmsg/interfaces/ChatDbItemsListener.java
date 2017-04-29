package com.igordubrovin.tfsmsg.interfaces;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Игорь on 28.04.2017.
 */

public interface ChatDbItemsListener {
    void itemAdded(BaseModel item);
    void itemsReceived(List<? extends BaseModel> items);
    void itemDeleted(BaseModel item);
}
