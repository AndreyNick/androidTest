package com.android.test.officeCrimesHolder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.test.officeCrimesHolder.domain.Crime;


public class CrimeFragment extends Fragment {
    private Crime crime;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }
}
