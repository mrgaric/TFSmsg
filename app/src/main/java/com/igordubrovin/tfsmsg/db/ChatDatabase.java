package com.igordubrovin.tfsmsg.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Игорь on 27.04.2017.
 */

@Database(name = ChatDatabase.NAME, version = ChatDatabase.VERSION)
public class ChatDatabase {
    public static final String NAME = "chat_db";
    public static final int VERSION = 1;

}
