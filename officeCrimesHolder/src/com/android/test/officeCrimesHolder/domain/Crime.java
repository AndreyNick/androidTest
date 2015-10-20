package com.android.test.officeCrimesHolder.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String SOLVED = "solved";
    private static final String DATE = "date";
    private static final String SUSPECT = "suspect";


    private UUID id;
    private String title;
    private Date date;
    private boolean solved;
    private String suspect;


    public Crime() {
        id = UUID.randomUUID();
        date = new Date(System.currentTimeMillis());
    }

    public Crime(String title, boolean solved) {
        this.title = title;
        this.solved = solved;
        id = UUID.randomUUID();
        date = new Date(System.currentTimeMillis());
    }

    public Crime(JSONObject json) throws JSONException {
        id = UUID.fromString(json.getString(ID));
        title = json.getString(TITLE);
        date = new Date(json.getLong(DATE));
        solved = json.getBoolean(SOLVED);
        suspect = json.getString(SUSPECT);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(ID, id.toString());
        json.put(TITLE, title);
        json.put(SOLVED, solved);
        json.put(DATE, date.getTime());
        json.put(SUSPECT, suspect);
        return json;
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

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    @Override
    public String toString() {
        return title;
    }
}
