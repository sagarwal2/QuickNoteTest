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
    private QuickNoteItem quickNoteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra(NOTE_ITEM)) {
            quickNoteItem = getIntent().getParcelableExtra(NOTE_ITEM);
            setTitle(getString(R.string.edit_note));
        } else {
            setTitle(getString(R.string.create_note));
        }

        setContentFragment(CreateEditNoteActivityFragment.newInstance(quickNoteItem));
        setBackEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_delete:
                CreateEditNoteActivityFragment editNoteActivityFragment = (CreateEditNoteActivityFragment)getSupportFragmentManager()
                        .findFragmentByTag(CreateEditNoteActivityFragment.class.getSimpleName());
                if(null != editNoteActivityFragment  && editNoteActivityFragment.isInLayout()) {
                    editNoteActivityFragment.deleteNote();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
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

    }

    @Override
    public void onNoteSaved() {

    }
}
