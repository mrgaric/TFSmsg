package com.igordubrovin.tfsmsg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.igordubrovin.tfsmsg.db.MessageItem;

/**
 * Created by Игорь on 16.04.2017.
 */

public class LoaderFragment extends Fragment implements LoaderManager.LoaderCallbacks <MessageItem> {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Loader<MessageItem> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<MessageItem> loader, MessageItem data) {

    }

    @Override
    public void onLoaderReset(Loader<MessageItem> loader) {

    }
}
