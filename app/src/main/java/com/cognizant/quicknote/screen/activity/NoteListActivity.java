package com.cognizant.quicknote.screen.activity;

import android.os.Bundle;
import android.view.View;

import com.cognizant.quicknote.R;
import com.cognizant.quicknote.model.QuickNoteItem;
import com.cognizant.quicknote.screen.BaseActivity;
import com.cognizant.quicknote.screen.fragment.NoteListActivityFragment;

public class NoteListActivity extends BaseActivity implements NoteListActivityFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_note_list);
        setTitle(getString(R.string.note_list));

        setPrimaryActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateEditNoteActivity.createIntent(NoteListActivity.this, null));
            }
        });
    }

    @Override
    public void onNoteListItemClicked(QuickNoteItem item) {
        startActivity(CreateEditNoteActivity.createIntent(this, item));
    }
}
