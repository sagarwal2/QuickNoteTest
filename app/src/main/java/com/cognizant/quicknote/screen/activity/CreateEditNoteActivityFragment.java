package com.cognizant.quicknote.screen.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cognizant.quicknote.R;
import com.cognizant.quicknote.helper.database.DataPresenter;
import com.cognizant.quicknote.helper.database.DataPresenterImpl;
import com.cognizant.quicknote.model.QuickNoteItem;

/**
 * Note create/edit fragment.
 */
public class CreateEditNoteActivityFragment extends Fragment {

    private static final String NOTE_ITEM = "CreateEditNoteActivityFragment#NOTE_ITEM";
    private OnFragmentInteractionListener mListener;
    private DataPresenterImpl dataPresenter;
    private TextView mNoteTitle;
    private EditText mNoteContent;
    private TextView mNoteModified;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataPresenter = new DataPresenterImpl(getActivity().getApplicationContext());
    }

    public CreateEditNoteActivityFragment() {
        // Required empty public constructor
    }

    public static CreateEditNoteActivityFragment newInstance(@Nullable QuickNoteItem item) {
        CreateEditNoteActivityFragment fragment = new CreateEditNoteActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_ITEM, item);
        fragment.setArguments(bundle);

        return fragment;
    }

    private QuickNoteItem getQuickNote() {
        QuickNoteItem noteItem = new QuickNoteItem();
        final String mNoteTitleText = mNoteTitle.getText().toString();
        final String mNoteContentText = mNoteContent.getText().toString();
        noteItem.setTitle(mNoteTitleText);
        noteItem.setContent(mNoteContentText);

        QuickNoteItem argNote = getArguments().getParcelable(NOTE_ITEM);
        if(null != argNote) {
            noteItem.setId(argNote.getId());
        }

        return noteItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_edit_note, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoteTitle = (EditText) view.findViewById(R.id.note_title);
        mNoteContent = (EditText) view.findViewById(R.id.note_content);
        mNoteModified = (TextView) view.findViewById(R.id.note_modified);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void deleteNote() {
        dataPresenter.deleteNote(getQuickNote(), new DataPresenter.onNoteUpdateCallback() {
            @Override
            public void onSuccess() {
                if(mListener != null) {
                    mListener.onNoteDeleted();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void saveNote() {
        dataPresenter.saveNote(getQuickNote(), new DataPresenter.onNoteUpdateCallback() {
            @Override
            public void onSuccess() {
                if (mListener != null) {
                    mListener.onNoteSaved();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onNoteDeleted();
        void onNoteEdited();
        void onNoteSaved();
    }


}
