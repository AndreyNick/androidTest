package com.android.test.officeCrimesHolder.domain;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID id;
    private String title;
    private Date date;
    private boolean solved;


    public Crime() {
        id = UUID.randomUUID();
        date = new Date(System.currentTimeMillis());
    }

    public Crime(String title, boolean solved) {
        this.title = title;
        this.solved = solved;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return title;
    }
}
