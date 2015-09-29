package com.android.test.officeCrimesHolder.fragment;


import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.test.officeCrimesHolder.R;

import java.io.IOException;
import java.util.List;


public class CrimeCameraFragment extends Fragment {

    private static final String TAG = "CrimeCameraFragment";

    private Camera camera;
    private SurfaceView surfaceView;

    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_camera, parent, false);
        Button takePictureButton = (Button)view.findViewById(R.id.crime_camera_takePictureButton);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        surfaceView = (SurfaceView)view.findViewById(R.id.crime_camera_surfaceView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(camera != null) {
                    try {
                        camera.setPreviewDisplay(surfaceHolder);
                    } catch (IOException e) {
                        Log.e(TAG, "Error setting up preview display", e);
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
                if(camera != null) return;
                //the size of area has changed
                //we need to renew the size of camera area
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = getBestSupportedSize(parameters.getSupportedPictureSizes(), w, h);
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);

                camera.startPreview();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if(camera != null) {
                    camera.stopPreview();
                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        camera.open(0);
    }

    @Override
    public void onPause() {
        super.onResume();
        if(camera != null) {
            camera.release();
            camera = null;
        }
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int w, int h) {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.height * bestSize.width;
        for(Camera.Size size : sizes) {
            int area = size.height * size.height;
            if(area > largestArea) {
                bestSize = size;
                largestArea = area;
            }
        }
        return bestSize;
    }
}
