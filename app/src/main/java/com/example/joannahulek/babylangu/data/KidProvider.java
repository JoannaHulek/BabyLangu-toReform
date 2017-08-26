package com.example.joannahulek.babylangu.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.joda.time.LocalDate;


import static android.R.attr.id;
import static com.example.joannahulek.babylangu.data.KidContract.CONTENT_AUTHORITY;
import static com.example.joannahulek.babylangu.data.KidContract.KidEntry.*;
import static com.example.joannahulek.babylangu.data.KidContract.PATH_KIDS;

/**
 * Created by Joasia on 15.08.2017.
 */

public class KidProvider extends ContentProvider {

    private static final String LOG_TAG = KidProvider.class.toString();
    private KidDdHelper mDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int KIDS = 100;
    private static final int KID_ID = 101;

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_KIDS, KIDS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_KIDS + "/#", KID_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new KidDdHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case KIDS:
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case KID_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KIDS:
                return CONTENT_LIST_TYPE;
            case KID_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KIDS:
                return insertKid(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not support for " + uri);
        }
    }

    private Uri insertKid(Uri uri, ContentValues values) {
        String name = values.getAsString(COLUMN_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Illegal name");
        }
        String birth = values.getAsString(COLUMN_BIRTH);
        if (birth == null) {
            throw new IllegalArgumentException("Illegal birth date");
        }
        String image_uri = values.getAsString(COLUMN_IMG_URI);
        if (image_uri == null) {
            throw new IllegalArgumentException("Illegal image uri");
        }
        String bg_colour = values.getAsString(COLUMN_BG_COLOR);
        if (bg_colour == null) {
            throw new IllegalArgumentException("Illegal background colour");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
//!!!TUTEJ DODAJESZ DO BAZY!!!
        long id = database.insert(KidContract.KidEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KIDS:
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case KID_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KIDS:
                return updateKid(uri, values, selection, selectionArgs);
            case KID_ID:
                selection = _ID + "?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateKid(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateKid(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(COLUMN_NAME)) {
            String name = values.getAsString(COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Illegal kid name");
            }
        }
        if (values.containsKey(COLUMN_BIRTH)) {
            LocalDate birth = LocalDate.parse(values.getAsString(COLUMN_BIRTH));
            if (birth == null) {
                throw new IllegalArgumentException("Illegal date of birth");
            }
        }
        if (values.containsKey(COLUMN_IMG_URI)) {
            String image_uri = values.getAsString(COLUMN_IMG_URI);
            if (image_uri == null) {
                throw new IllegalArgumentException("Illegal image uri");
            }
        }
        if (values.containsKey(COLUMN_BG_COLOR)) {
            String bg_colour = values.getAsString(COLUMN_BG_COLOR);
            if (bg_colour == null) {
                throw new IllegalArgumentException("Illegal background colour");
            }
        }

        if (values.size() == 0) {// v  m   gff  fc   c c   <- code by Mimi :-)
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
