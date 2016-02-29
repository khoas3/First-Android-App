package com.example.khoa.firstapplication;

/**
 * Created by khoa.nguyen on 2/29/2016.
 */
public class Post {
    private long id;
    public String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return text;
    }

    public void setComment(String text) {
        this.text = text;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return text;
    }
}
