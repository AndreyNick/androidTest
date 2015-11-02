package com.example.photoGallery;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class PictureDownloader<Token> extends HandlerThread {
    private static final String TAG = "PictureDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    Handler handler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());

    public PictureDownloader() {
        super(TAG);
    }

    public void queuePicture(Token token, String url) {
        Log.i(TAG, "Got an url: " + url);
        requestMap.put(token, url);
        handler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_DOWNLOAD) {
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    private void handleRequest(final Token token) {
        try {
        final String url = requestMap.get(token);
        if(url == null) {
            return;
        }

            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");

        } catch (IOException e) {
            Log.e(TAG, "Error downloading image", e);
        }
    }
}