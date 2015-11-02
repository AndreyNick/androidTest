package com.example.photoGallery;


import android.os.HandlerThread;
import android.util.Log;


public class PictureDownloader<Token> extends HandlerThread {
    private static final String TAG = "PictureDownloader";

    public PictureDownloader() {
        super(TAG);
    }

    public void queuePicture(Token token, String url) {
        Log.i(TAG, "Got an url: " + url);
    }
}
