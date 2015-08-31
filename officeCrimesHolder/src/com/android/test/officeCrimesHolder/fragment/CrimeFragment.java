package com.android.test.officeCrimesHolder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.android.test.officeCrimesHolder.R;
import com.android.test.officeCrimesHolder.domain.Crime;



public class CrimeFragment extends Fragment {
    private Crime crime;
    private EditText textField;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState){
        View v = this.getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_crime, parent, false);  //TODO it have to be inflater.inflate
        textField = (EditText)v.findViewById(R.id.crime_title);
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                crime.setTitle(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;

    }
}
