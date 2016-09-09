package com.bignerdranch.android.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Vladimir on 05.09.2016.
 */
public class Crime {
    private static final String CRIME_DATE_FORMAT = "EEEE, MMM dd, yyyy"; // Tuesday, Sep 06, 2016
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    private SimpleDateFormat mCrimeDateFormat;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mCrimeDateFormat = new SimpleDateFormat(CRIME_DATE_FORMAT);
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
}
