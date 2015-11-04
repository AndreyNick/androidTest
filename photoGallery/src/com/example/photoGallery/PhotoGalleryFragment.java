package com.example.photoGallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private GridView gridView;
    ArrayList<GalleryItem> items;
    PictureDownloader<ImageView> pictureDownloader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();

        pictureDownloader = new PictureDownloader(new Handler());
        pictureDownloader.setListener(new PictureDownloader.Listener<ImageView>() {
            @Override
            public void onPictureDownloaded(ImageView imageView, Bitmap picture) {
                if(isVisible()) {
                    imageView.setImageBitmap(picture);
                }
            }
        });
        pictureDownloader.start();
        pictureDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        gridView = (GridView)v.findViewById(R.id.gridView);
        setupAdapter();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pictureDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    void setupAdapter() {
        if(getActivity() == null || gridView == null) return;

        if(items != null) {
            gridView.setAdapter(new GalleryItemAdapter(items));
        } else {
            gridView.setAdapter(null);
        }
    }

    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {

        public GalleryItemAdapter(ArrayList<GalleryItem> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.gallery_item,
                        parent, false);
            }

            ImageView imageView = (ImageView)convertView.findViewById(R.id.gallery_item_imageView);
            imageView.setImageResource(R.drawable.loading);

            GalleryItem item = getItem(position);
            pictureDownloader.queuePicture(imageView, item.getUrl());

            return imageView;
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {

        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... voids) {
            return new FlickrFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> list) {
            items = list;
            setupAdapter();
        }
    }


}
