package com.android.test.officeCrimesHolder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.android.test.officeCrimesHolder.R;
import com.android.test.officeCrimesHolder.activity.CrimeActivity;
import com.android.test.officeCrimesHolder.activity.CrimeListActivity;
import com.android.test.officeCrimesHolder.domain.Crime;
import com.android.test.officeCrimesHolder.domain.CrimeLab;

import java.util.List;

public class CrimeListFragment extends ListFragment {

    private List<Crime> crimesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        setHasOptionsMenu(true);
        crimesList = CrimeLab.getInstance(getActivity()).getAllCrimes();
        CrimeAdapter crimeAdapter = new CrimeAdapter(crimesList);
        setListAdapter(crimeAdapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(), CrimeActivity.class);
        intent.putExtra(CrimeFragment.CRIME_ID, crime.getId());
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);
                Intent intent = new Intent(getActivity(), CrimeListActivity.class);
                intent.putExtra(CrimeFragment.CRIME_ID, crime.getId());
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(List<Crime> crimesList) {
            super(getActivity(), 0, crimesList);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }
            Crime crime = getItem(position);
            TextView titleTextView = (TextView)view.findViewById(R.id.list_item_crime_text_view_title);
            titleTextView.setText(crime.getTitle());
            TextView dateTextView = (TextView)view.findViewById(R.id.list_item_crime_text_view_date);
            dateTextView.setText(DateFormat.format("dd.MM.yy", crime.getDate()));
            CheckBox solvedCheckBox = (CheckBox)view.findViewById(R.id.list_item_crime_checkbox);
            solvedCheckBox.setChecked(crime.getSolved());
            return view;
        }
    }
}
