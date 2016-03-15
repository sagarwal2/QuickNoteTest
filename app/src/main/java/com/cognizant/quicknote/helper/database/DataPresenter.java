package com.cognizant.quicknote.helper.database;

import com.cognizant.quicknote.model.QuickNoteItem;

import java.util.List;

public interface DataPresenter {

    void fetchNoteList(OnNoteListFetchedCallback callback);

    void deleteNote(QuickNoteItem quickNote, onNoteUpdateCallback callback);

    void saveNote(QuickNoteItem quickNote, onNoteUpdateCallback onNoteUpdateCallback);

    interface OnDataHandlerError {
        void onError(Exception e);
    }
    interface OnNoteListFetchedCallback extends OnDataHandlerError{
        void onSuccess(List<QuickNoteItem> noteItems);
    }
    interface onNoteUpdateCallback extends OnDataHandlerError {
        void onSuccess();
    }

}