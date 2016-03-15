package com.cognizant.quicknote.helper.database;

import android.content.Context;

import com.cognizant.quicknote.model.QuickNoteItem;

import java.lang.ref.WeakReference;
import java.text.ParseException;

public class DataPresenterImpl implements DataPresenter {
    private final WeakReference<Context> mContext;

    public DataPresenterImpl(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public void fetchNoteList(OnNoteListFetchedCallback callback) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(mContext.get());
        try {
            callback.onSuccess(databaseHelper.fetchAllNotes());
        } catch (IllegalArgumentException|ParseException e) {
            callback.onError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNote(QuickNoteItem quickNote, onNoteUpdateCallback callback) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(mContext.get());
        if(databaseHelper.deleteNote(quickNote)) {
            callback.onSuccess();
        } else {
            callback.onError(new Exception("Nothing deleted"));
        }
    }

    @Override
    public void saveNote(QuickNoteItem quickNote, onNoteUpdateCallback onNoteUpdateCallback) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(mContext.get());
        if(databaseHelper.saveNote(quickNote)) {
            onNoteUpdateCallback.onSuccess();
        } else {
            onNoteUpdateCallback.onError(new Exception("Nothing updated"));
        }
    }
}
