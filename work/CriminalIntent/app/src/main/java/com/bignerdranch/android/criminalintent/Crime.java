package com.bignerdranch.android.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Vladimir on 05.09.2016.
 */
public class Crime {
    private static final String CRIME_DATE_FORMAT = "EEEE, MMM dd, yyyy"; // Tuesday, Sep 06, 2016
    private static final String CRIME_TIME_FORMAT = " HH:mm"; // 20:23
    private static final String CRIME_DATE_TIME_FORMAT = CRIME_DATE_FORMAT + CRIME_TIME_FORMAT; // Tuesday, Sep 06, 2016 20:23

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    private SimpleDateFormat mCrimeDateFormat;
    private SimpleDateFormat mCrimeTimeFormat;
    private SimpleDateFormat mCrimeDateTimeFormat;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mCrimeDateFormat = new SimpleDateFormat(CRIME_DATE_FORMAT);
        mCrimeTimeFormat = new SimpleDateFormat(CRIME_TIME_FORMAT);
        mCrimeDateTimeFormat = new SimpleDateFormat(CRIME_DATE_TIME_FORMAT);
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDateAsString() {
        return mCrimeDateFormat.format(getDate());
    }

    public String getTimeAsString() {
        return mCrimeTimeFormat.format(getDate());
    }

    public String getDateTimeAsString() {
        return mCrimeDateTimeFormat.format(getDate());
    }
}
