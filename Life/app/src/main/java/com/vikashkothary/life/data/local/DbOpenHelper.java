package com.vikashkothary.life.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vikashkothary.life.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "life.db";
    public static final int DATABASE_VERSION = 1;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        //Uncomment line below if you want to enable foreign keys
        //db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(Db.RibotProfileTable.CREATE);
            db.execSQL(Db.RemindersTable.CREATE);
            //Add other tables here
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        db.beginTransaction();
        try {
            db.execSQL(Db.RibotProfileTable.DELETE);
            db.execSQL(Db.RemindersTable.DELETE);
            //Add other tables here
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        onCreate(db);
    }

}
