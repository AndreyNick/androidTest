package com.android.test.officeCrimesHolder.activity;

import android.support.v4.app.Fragment;
import com.android.test.officeCrimesHolder.fragment.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}