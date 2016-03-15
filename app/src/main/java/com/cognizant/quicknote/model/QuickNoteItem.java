package com.cognizant.quicknote.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Parcelable model to hold Note data
 */
public class QuickNoteItem implements Parcelable {

    private int id;
    private String title;
    private String content;
    private Date modified;

    public QuickNoteItem() {
    }

    public QuickNoteItem(int id, String title, String content, Date modified) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * Un-marshall attributes
     * @param in parcel to read from
     */
    protected QuickNoteItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        modified = new Date(in.readLong());
    }

    public static final Creator<QuickNoteItem> CREATOR = new Creator<QuickNoteItem>() {
        @Override
        public QuickNoteItem createFromParcel(Parcel in) {
            return new QuickNoteItem(in);
        }

        @Override
        public QuickNoteItem[] newArray(int size) {
            return new QuickNoteItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Marshall attributes to parcel
     * @param dest parcel to write to
     * @param flags additional flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(modified.getTime());
    }
}
