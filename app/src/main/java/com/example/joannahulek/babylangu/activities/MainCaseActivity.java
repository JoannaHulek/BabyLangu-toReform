package com.example.joannahulek.babylangu.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.joannahulek.babylangu.R;
import com.example.joannahulek.babylangu.adapters.KidCursorAdapter;
import com.example.joannahulek.babylangu.data.KidContract;

public class MainCaseActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private KidCursorAdapter kidCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_case);

        kidCursorAdapter = new KidCursorAdapter(this, null);

        ListView kidsListView = (ListView) findViewById(R.id.kids_list_view);
        kidsListView.setEmptyView(findViewById(R.id.empty_view));
        kidsListView.setAdapter(kidCursorAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

        FloatingActionButton addFab = (FloatingActionButton) findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCaseActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                KidContract.KidEntry._ID,
                KidContract.KidEntry.COLUMN_NAME,
                KidContract.KidEntry.COLUMN_BIRTH,
                KidContract.KidEntry.COLUMN_IMG_URI,
                KidContract.KidEntry.COLUMN_BG_COLOR};
        return new CursorLoader(this, KidContract.KidEntry.CONTENT_URI, projection, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        kidCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        kidCursorAdapter.swapCursor(null);
    }
}