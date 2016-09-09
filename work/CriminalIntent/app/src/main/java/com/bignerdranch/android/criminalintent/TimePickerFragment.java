package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Vladimir on 09.09.2016.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.bignerdranch.android.criminalintent.time";
    private static final String ARG_TIME = "time";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View view = LayoutInflater.from(getActivity())
            .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_time_picker);

        if (mTimePicker != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTimePicker.setHour(hour);
                mTimePicker.setMinute(minute);
            }
            else {
                mTimePicker.setCurrentHour(hour);
                mTimePicker.setCurrentMinute(minute);
            }
        }

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int hour;
                    int minute;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = mTimePicker.getHour();
                        minute = mTimePicker.getMinute();
                    }
                    else {
                        hour = mTimePicker.getCurrentHour();
                        minute = mTimePicker.getCurrentMinute();
                    }

                    Date date = new GregorianCalendar(year, month, day, hour, minute).getTime();
                    sendResult(Activity.RESULT_OK, date);
                }
            })
            .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
