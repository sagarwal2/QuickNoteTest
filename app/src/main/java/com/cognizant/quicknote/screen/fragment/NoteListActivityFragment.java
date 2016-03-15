package com.cognizant.quicknote.screen.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cognizant.quicknote.R;
import com.cognizant.quicknote.helper.database.DataListener;
import com.cognizant.quicknote.helper.database.DataPresenter;
import com.cognizant.quicknote.helper.database.DataPresenterImpl;
import com.cognizant.quicknote.model.QuickNoteItem;
import com.cognizant.quicknote.screen.adapter.QuickNoteAdapter;

import java.util.List;

/**
 * NoteList fragment containing list of notes.
 */
public class NoteListActivityFragment extends Fragment implements DataListener, QuickNoteAdapter.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private DataPresenter dataPresenter;

    public NoteListActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataPresenter = new DataPresenterImpl(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.note_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        dataPresenter.fetchNoteList(new DataPresenter.OnNoteListFetchedCallback() {
            @Override
            public void onSuccess(List<QuickNoteItem> noteItems) {
                if(mRecyclerView.getAdapter() == null) {
                    final QuickNoteAdapter quickNoteAdapter = new QuickNoteAdapter(noteItems);
                    quickNoteAdapter.SetOnItemClickListener(NoteListActivityFragment.this);
                    mRecyclerView.setAdapter(quickNoteAdapter);
                } else {
                    ((QuickNoteAdapter)mRecyclerView.getAdapter()).setNoteList(noteItems);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
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

    @Override
    public void onItemClick(QuickNoteItem item) {
        mListener.onNoteListItemClicked(item);
    }

    public interface OnFragmentInteractionListener {
        void onNoteListItemClicked(QuickNoteItem item);
    }

}
