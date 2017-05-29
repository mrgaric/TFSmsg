package com.igordubrovin.tfsmsg.firebase;

import java.util.List;

/**
 * Created by Игорь on 26.05.2017.
 */

public interface DialogItemValueListener<T> {

    void onValue(List<T> items);
}
