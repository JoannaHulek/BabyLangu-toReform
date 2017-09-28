package com.example.joannahulek.babylangu.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.joannahulek.babylangu.R;

import java.util.Calendar;


/**
 * Created by joasiahulek on 9/28/17.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        TextView tv = (TextView) getActivity().findViewById(R.id.birth_input);
        month++;
        if (month < 10) {
            tv.setText(tv.getText() + " " + year + "-0" + month + "-" + dayOfMonth);
        } else {
            tv.setText(tv.getText() + " " + year + "-" + month + "-" + dayOfMonth);
        }
    }
}
