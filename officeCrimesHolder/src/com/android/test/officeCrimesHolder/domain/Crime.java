package com.android.test.officeCrimesHolder.domain;

import java.util.UUID;

/**
 * Created by anni0913 on 31.08.2015.
 */
public class Crime {

    private UUID id;
    private String title;

    public Crime() {
        id = UUID.randomUUID();
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
}
