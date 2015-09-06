package com.android.test.officeCrimesHolder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.android.test.officeCrimesHolder.R;
import com.android.test.officeCrimesHolder.domain.Crime;
import com.android.test.officeCrimesHolder.domain.CrimeLab;

import java.util.UUID;

public class CrimeFragment extends Fragment {
    public static final String CRIME_ID = "com.android.test.officeCrimesHolder.fragment.CrimeFragment.crime_id";
    private static final String DIALOG_DATE = "date";
    private Crime crime;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getActivity().getIntent()
                .getSerializableExtra(CRIME_ID);
        crime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState){
        View v = layoutInflater.inflate(R.layout.fragment_crime, parent, false);
        EditText textField = (EditText) v.findViewById(R.id.crime_title);
        textField.setText(crime.getTitle());
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
        Button dateButton = (Button) v.findViewById(R.id.crime_date);
        dateButton.setText(DateFormat.format("dd.mm.yy",crime.getDate()));
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(fragmentManager, DIALOG_DATE);

            }
        });
        CheckBox solvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        solvedCheckbox.setChecked(crime.getSolved());
        solvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                crime.setSolved(b);
            }
        });
        return v;

    }
}
