package com.android.test.officeCrimesHolder.activity;

import android.support.v4.app.Fragment;

import com.android.test.officeCrimesHolder.fragment.CrimeCameraFragment;

public class CrimeCameraActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}
