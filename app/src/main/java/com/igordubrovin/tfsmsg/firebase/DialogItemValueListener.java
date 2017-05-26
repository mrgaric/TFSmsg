package com.igordubrovin.tfsmsg.firebase;

import com.igordubrovin.tfsmsg.utils.DialogItem;

import java.util.List;

/**
 * Created by Игорь on 26.05.2017.
 */

public interface DialogItemValueListener {

    void onValue(List<DialogItem> items);
}
