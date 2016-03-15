package com.cognizant.quicknote.screen.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cognizant.quicknote.R;
import com.cognizant.quicknote.model.QuickNoteItem;

import java.text.DateFormat;
import java.util.List;

public class QuickNoteAdapter extends RecyclerView.Adapter<QuickNoteAdapter.NoteViewHolder> {
    private List<QuickNoteItem> noteItemList;

    public QuickNoteAdapter(@NonNull List<QuickNoteItem> noteItemList) {
        this.noteItemList = noteItemList;
    }

    public void setNoteList(List<QuickNoteItem> noteItems) {
        this.noteItemList = noteItems;
        notifyDataSetChanged();
    }

    @Override
    public QuickNoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_row, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuickNoteAdapter.NoteViewHolder holder, int position) {
        QuickNoteItem noteItem = noteItemList.get(position);
        holder.noteTitle.setText(noteItem.getTitle());
        holder.noteBody.setText(noteItem.getContent());
        holder.noteDateModified.setText(DateFormat.getDateTimeInstance().format(noteItem.getModified()));
    }

    @Override
    public int getItemCount() {
        return noteItemList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        protected final TextView noteTitle;
        protected final TextView noteBody;
        protected final TextView noteDateModified;

        public NoteViewHolder(View itemView) {
            super(itemView);
            this.noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            this.noteBody = (TextView) itemView.findViewById(R.id.note_line_body);
            this.noteDateModified = (TextView) itemView.findViewById(R.id.note_modified);
        }

    }

}