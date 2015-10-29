package com.example.photoGallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    private static final String TAG = "PhotoGalleryActivity";

    @Override
    protected Fragment createFragment() {
        return new PhotoGalleryFragment();
    }
}