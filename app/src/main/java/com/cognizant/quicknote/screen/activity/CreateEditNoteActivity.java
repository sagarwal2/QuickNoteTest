package com.cognizant.quicknote.screen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.cognizant.quicknote.R;
import com.cognizant.quicknote.model.QuickNoteItem;
import com.cognizant.quicknote.screen.BaseActivity;

public class CreateEditNoteActivity extends BaseActivity implements CreateEditNoteActivityFragment.OnFragmentInteractionListener{

    private static final String NOTE_ITEM = "CreateEditNoteActivity#NOTE_ITEM";
    private static final String FRAGMENT_TAG = CreateEditNoteActivityFragment.class.getSimpleName();
    private QuickNoteItem quickNoteItem;
    private enum NOTE_ACTION {SAVE, DELETE};
    private NOTE_ACTION note_action = NOTE_ACTION.DELETE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra(NOTE_ITEM)) {
            quickNoteItem = getIntent().getParcelableExtra(NOTE_ITEM);
            setTitle(getString(R.string.edit_note));
        } else {
            setTitle(getString(R.string.create_note));
        }

        setContentFragment(CreateEditNoteActivityFragment.newInstance(quickNoteItem), FRAGMENT_TAG);
        setBackEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CreateEditNoteActivityFragment editNoteActivityFragment = (CreateEditNoteActivityFragment)getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG);
        switch(item.getItemId()) {
            case R.id.action_delete:
                if(null != editNoteActivityFragment  && editNoteActivityFragment.isVisible()) {
                    editNoteActivityFragment.deleteNote();
                }

                return true;

            case R.id.action_save:
                if(null != editNoteActivityFragment  && editNoteActivityFragment.isVisible()) {
                    editNoteActivityFragment.saveNote();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (note_action) {
            case SAVE:
                getMenuInflater().inflate(R.menu.menu_create_save_note, menu);
                return true;

            case DELETE:
                getMenuInflater().inflate(R.menu.menu_create_edit_note, menu);
                return true;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public static Intent createIntent(Context context, @Nullable QuickNoteItem noteItem) {
         return
                 new Intent(context, CreateEditNoteActivity.class)
                .putExtra(NOTE_ITEM, noteItem);
    }

    @Override
    public void onNoteDeleted() {
        finish();
    }

    @Override
    public void onNoteEdited() {
        note_action = NOTE_ACTION.SAVE;
        invalidateOptionsMenu();
    }

    @Override
    public void onNoteSaved() {
        note_action = NOTE_ACTION.DELETE;
        invalidateOptionsMenu();
    }
}
