package com.android.test.officeCrimesHolder.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.*;
import android.widget.AdapterView;
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
    private boolean subtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setTitle(R.string.crimes_title);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        subtitleVisible = false;
        crimesList = CrimeLab.getInstance(getActivity()).getAllCrimes();
        CrimeAdapter crimeAdapter = new CrimeAdapter(crimesList);
        setListAdapter(crimeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //getActivity().setTitle(R.string.crimes_title);
        //setHasOptionsMenu(true);
        //setRetainInstance(true);
        //subtitleVisible = false;
        crimesList = CrimeLab.getInstance(getActivity()).getAllCrimes();
        CrimeAdapter crimeAdapter = new CrimeAdapter(crimesList);
        setListAdapter(crimeAdapter);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        //for the regular ListFragment ListView adding

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(subtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        registerForContextMenu(listView);
        return inflater.inflate(R.layout.fragment_list_crime, null);
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
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(subtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    //Overall we don't need this annotation above
    //because 'Subtitle' button is in fragment_crime_list.xml menu for API above 11 only
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
            case R.id.menu_item_show_subtitle:
                if(getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    subtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    getActivity().getActionBar().setSubtitle(null);
                    subtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
        Crime crime = adapter.getItem(position);

        switch (menuItem.getItemId()) {
            case R.id.menu_item_delete_crime:
                CrimeLab.getInstance(getActivity()).deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        } return super.onContextItemSelected(menuItem);




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
            dateTextView.setText(DateFormat.format("MMMM dd, yyyy", crime.getDate()));
            CheckBox solvedCheckBox = (CheckBox)view.findViewById(R.id.list_item_crime_checkbox);
            solvedCheckBox.setChecked(crime.getSolved());
            return view;
        }

    }
}
