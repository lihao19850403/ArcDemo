package com.lihao.en_common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Diary {

    private String mId;

    private String mTitle;

    private String mDescription;

    private List<Observer<Diary>> mObservers;

    public Diary(String title, String description) {
        this(UUID.randomUUID().toString(), title, description);
    }

    public Diary(String id, String title, String description) {
        mId = id;
        mTitle = title;
        mDescription = description;
    }

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
        notifyObservers();
    }

    public void setDescription(String description) {
        mDescription = description;
        notifyObservers();
    }

    public void registerObserver(Observer<Diary> observer) {
        getObservers().add(observer);
    }

    public void notifyObservers() {
        for (Observer<Diary> observer : getObservers()) {
            observer.update(this);
        }
    }

    private List<Observer<Diary>> getObservers() {
        if (mObservers == null) {
            mObservers = new ArrayList<>();
        }
        return mObservers;
    }
}
