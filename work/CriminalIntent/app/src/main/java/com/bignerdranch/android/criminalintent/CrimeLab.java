package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vladimir on 06.09.2016.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        String orderBy = CrimeTable.Cols.SOLVED + ", "  + CrimeTable.Cols.DATE + " desc";

        Cursor cursor = mDatabase.query(
            CrimeTable.NAME,
            null,           // Columns - null selects all columns
            whereClause,
            whereArgs,
            null,           // groupBy
            null,           // having
            orderBy
        );

        return new CrimeCursorWrapper(cursor);
    }

    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {
        String whereClause = CrimeTable.Cols.UUID + " = ? ";
        String[] whereArgs = new String[] { id.toString() };

        CrimeCursorWrapper cursor = queryCrimes(whereClause, whereArgs);
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        String whereClause = CrimeTable.Cols.UUID + " = ? ";
        String[] whereArgs = new String[]{uuidString};
        mDatabase.update(CrimeTable.NAME, values, whereClause, whereArgs);
    }

    public int deleteCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        String whereClause = CrimeTable.Cols.UUID + " = ? ";
        String[] whereArgs = new String[] { uuidString };

        return mDatabase.delete(CrimeTable.NAME, whereClause, whereArgs);
    }
}
