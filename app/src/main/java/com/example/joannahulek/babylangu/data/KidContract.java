package com.example.joannahulek.babylangu.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Joasia on 15.08.2017.
 */

public final class KidContract {

    public static final String CONTENT_AUTHORITY = "com.example.joannahulek.babylangu";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_KIDS = "kids";

    private KidContract() {
        throw new AssertionError("No kids instances");
    }

    public static final class KidEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_KIDS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_KIDS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_KIDS;

        public final static String TABLE_NAME = "kids";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_BIRTH = "birth";
        public final static String COLUMN_IMAGE_URI = "image_uri";
        public final static String COLUMN_BG_COLOUR = "bg_colour";
    }
}
