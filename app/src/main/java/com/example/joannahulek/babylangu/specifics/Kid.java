package com.example.joannahulek.babylangu.specifics;

import android.content.ContentValues;

import com.example.joannahulek.babylangu.data.KidContract;

import org.joda.time.LocalDate;

/**
 * Created by Joasia on 19.08.2017.
 */

public class Kid {

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public String getBg_color() {
        return bg_color;
    }

    private final Integer id;
    private final String name;
    private final LocalDate birth;
    private final String img_uri;
    private final String bg_color;

    public Kid(Integer id, String name, LocalDate birth, String img_uri, String bg_color) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.img_uri = img_uri;
        this.bg_color = bg_color;
    }

    public ContentValues transformToContentValues() {
        ContentValues values = new ContentValues();
        values.put(KidContract.KidEntry.COLUMN_NAME, name);
        values.put(KidContract.KidEntry.COLUMN_BIRTH, birth.toString());
        values.put(KidContract.KidEntry.COLUMN_IMG_URI, img_uri);
        values.put(KidContract.KidEntry.COLUMN_BG_COLOR, bg_color);
        return values;
    }
}
