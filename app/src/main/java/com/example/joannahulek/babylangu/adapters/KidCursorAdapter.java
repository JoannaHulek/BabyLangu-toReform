package com.example.joannahulek.babylangu.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joannahulek.babylangu.R;
import com.example.joannahulek.babylangu.data.KidContract;
import com.example.joannahulek.babylangu.specifics.Kid;

import org.joda.time.LocalDate;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by Joasia on 18.08.2017.
 */

public class KidCursorAdapter extends CursorAdapter implements Serializable{

    private final Context context;

    public KidCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView itemMainText = (TextView) view.findViewById(R.id.item_main_text);
        TextView itemDescriptionText = (TextView) view.findViewById(R.id.item_description_text);

        final Kid kid = getKidFromCursor(cursor);

        itemMainText.setText(kid.getName());
        itemDescriptionText.setText(kid.getBirth().toString());
    }

    private Kid getKidFromCursor (final Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndex(KidContract.KidEntry.COLUMN_NAME));
        LocalDate birth = LocalDate.parse(cursor.getString(cursor.getColumnIndex(KidContract.KidEntry.COLUMN_BIRTH)));
        String img_uri = cursor.getString(cursor.getColumnIndex(KidContract.KidEntry.COLUMN_IMG_URI));
        String bg_color = cursor.getString(cursor.getColumnIndex(KidContract.KidEntry.COLUMN_BG_COLOR));

        return new Kid(id, name, birth, img_uri, bg_color);
    }
}
