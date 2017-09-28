package com.example.joannahulek.babylangu.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joannahulek.babylangu.R;
import com.example.joannahulek.babylangu.data.KidContract;
import com.example.joannahulek.babylangu.specifics.Kid;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        addInteractions();
    }

    private void addInteractions() {
        final EditText wordInput = (EditText) findViewById(R.id.word_input);
        final EditText meaningInput = (EditText) findViewById(R.id.meaning_input);

        final EditText nameInput = (EditText) findViewById(R.id.name_input);
        final EditText birthInput = (EditText) findViewById(R.id.birth_input);

        findViewById(R.id.add_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNew(wordInput, meaningInput, nameInput, birthInput);
            }
        });
    }

    private void addNew(EditText wordInput, EditText meaningInput,
                        EditText nameInput, EditText birthInput) {
        Kid kid = null;
        Uri kidUri = null;
        try {
            String name = nameInput.getText().toString();
            LocalDate birth = LocalDate.parse(birthInput.getText().toString());
            String img_uri = "NOT NULL";
            String bg_color = "NOT NULL";
            kid = new Kid(null, name, birth, img_uri, bg_color);
            kidUri = getContentResolver().insert(KidContract.KidEntry.CONTENT_URI, kid.transformToContentValues());
            Toast.makeText(EditorActivity.this, getString(R.string.kid_saved), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditorActivity.this, MainCaseActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("EDITORSACTIVITY", "jakis exception", e);
            Toast.makeText(EditorActivity.this, getString(R.string.kid_not_saved), Toast.LENGTH_SHORT).show();
        }
    }
}
