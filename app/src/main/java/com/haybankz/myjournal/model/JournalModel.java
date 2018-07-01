package com.haybankz.myjournal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class JournalModel implements Parcelable {

    private String key;

    private String title;

    private String content;

    private long createdOn;

    public JournalModel() {
    }

    public JournalModel(String key, String title, String content, long createdOn) {
        this.key = key;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public JournalModel(Parcel in){
        this.key = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.createdOn = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeLong(this.createdOn);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public JournalModel createFromParcel(Parcel in){
            return new JournalModel(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

}
