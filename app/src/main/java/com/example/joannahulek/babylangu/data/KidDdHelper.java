package com.example.joannahulek.babylangu.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.joannahulek.babylangu.data.KidContract.KidEntry.COLUMN_BG_COLOR;
import static com.example.joannahulek.babylangu.data.KidContract.KidEntry.COLUMN_BIRTH;
import static com.example.joannahulek.babylangu.data.KidContract.KidEntry.COLUMN_IMG_URI;
import static com.example.joannahulek.babylangu.data.KidContract.KidEntry.COLUMN_NAME;
import static com.example.joannahulek.babylangu.data.KidContract.KidEntry.TABLE_NAME;

/**
 * Created by Joasia on 15.08.2017.
 */

class KidDdHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_KIDS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_BIRTH + " DATE NOT NULL, "
            + COLUMN_IMG_URI + " TEXT NOT NULL, "
            + COLUMN_BG_COLOR + " TEXT NOT NULL);";
    private static final String DATABASE_NAME = "kidslist.dp";
    private static final int DATABASE_VERSION = 3;

    public KidDdHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_KIDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_NAME);
        db.execSQL(SQL_CREATE_KIDS_TABLE);
    }
}
