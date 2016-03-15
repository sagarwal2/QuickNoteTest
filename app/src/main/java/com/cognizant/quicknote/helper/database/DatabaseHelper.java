package com.cognizant.quicknote.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cognizant.quicknote.helper.Utility;
import com.cognizant.quicknote.model.QuickNoteItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "QuickNoteItems.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUICK_NOTES = "QuickNotes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_DATE_MODIFIED = "modified";

    private static DatabaseHelper me;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table " + TABLE_QUICK_NOTES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_CONTENT + " text, "
            + COLUMN_DATE_MODIFIED + ", datetime default CURRENT_TIMESTAMP);";


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if(me == null) {
            me = new DatabaseHelper(context);
        }
        return me;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUICK_NOTES);
        onCreate(db);
    }

    /**
     * Fetch all user notes
     */
    public List<QuickNoteItem> fetchAllNotes() throws IllegalArgumentException, ParseException {
        List<QuickNoteItem> noteItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_QUICK_NOTES;
        Log.e(LOG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        //noinspection TryFinallyCanBeTryWithResources
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    QuickNoteItem noteItem = new QuickNoteItem();
                    noteItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    noteItem.setTitle((cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))));
                    noteItem.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
                    noteItem.setModified(Utility.getDateFromString(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_MODIFIED))));

                    noteItemList.add(noteItem);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }


        return noteItemList;
    }

    public boolean deleteNote(@Nullable QuickNoteItem quickNote) {
        int returnable = 0;
        if(null != quickNote) {
            SQLiteDatabase db = this.getWritableDatabase();
            returnable = db.delete(TABLE_QUICK_NOTES, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(quickNote.getId())});
        }

        return returnable > 0;
    }

    public boolean saveNote(@NonNull QuickNoteItem quickNote) {
        long returnable = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, quickNote.getTitle());
        values.put(COLUMN_CONTENT, quickNote.getContent());
        values.put(COLUMN_DATE_MODIFIED, DateFormat.getDateTimeInstance().format(quickNote.getModified()));

        if(hasNote(quickNote.getId())) {
            // updating row
            returnable = db.update(TABLE_QUICK_NOTES, values, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(quickNote.getId())});
        } else {
            // insert row
            returnable = db.insert(TABLE_QUICK_NOTES, null, values);
        }

        return returnable > 0;
    }

    public boolean hasNote(long note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_QUICK_NOTES + " WHERE "
                + COLUMN_ID + " = " + note_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        return c != null && c.moveToFirst();

    }
}
