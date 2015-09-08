package com.android.test.officeCrimesHolder.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.android.test.officeCrimesHolder.R;
import com.android.test.officeCrimesHolder.domain.Crime;
import com.android.test.officeCrimesHolder.domain.CrimeLab;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    public static final String CRIME_ID = "com.android.test.officeCrimesHolder.fragment.CrimeFragment.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private Crime crime;
    private Button dateButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID crimeId = (UUID)getActivity().getIntent()
                .getSerializableExtra(CRIME_ID);
        crime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode != Activity.RESULT_OK) return;
        if(requestCode == REQUEST_DATE){
            Date date = (Date) intent.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(date);
            updateDate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState){
        View v = layoutInflater.inflate(R.layout.fragment_crime, parent, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
        dateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.getInstance(crime.getDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
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

    private void updateDate() {
        dateButton.setText(DateFormat.format("dd.MM.yy",crime.getDate()));
    }
}
