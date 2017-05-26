package com.igordubrovin.tfsmsg.firebase;

/**
 * Created by Игорь on 26.05.2017.
 */

public interface OnTransactionComplete<T> {
    void onCommit(T result);

    void onAbort(Exception e);
}
