package com.lihao.arcdemo.models;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary")
public class Diary {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "diaryId")
    private String mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "desc")
    private String mDescription;

    @Ignore
    public Diary(String title, String description) {
        this(UUID.randomUUID().toString(), title, description);
    }

    public Diary(String id, String title, String description) {
        mId = id;
        mTitle = title;
        mDescription = description;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
