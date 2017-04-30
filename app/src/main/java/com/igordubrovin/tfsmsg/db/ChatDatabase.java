package com.igordubrovin.tfsmsg.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by Игорь on 27.04.2017.
 */

@Database(name = ChatDatabase.NAME, version = ChatDatabase.VERSION)
public class ChatDatabase {
    public static final String NAME = "chat_db";
    public static final int VERSION = 2;

    @Migration(version = VERSION, database = ChatDatabase.class)
    public static class ChatDatabaseMigration1 extends AlterTableMigration<DialogItem>{

        public ChatDatabaseMigration1(Class<DialogItem> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            super.onPreMigrate();
            addColumn(SQLiteType.TEXT, "lastMessage");
        }
    }
}
