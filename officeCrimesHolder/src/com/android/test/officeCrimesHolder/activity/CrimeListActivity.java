package com.android.test.officeCrimesHolder.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.test.officeCrimesHolder.R;
import com.android.test.officeCrimesHolder.domain.Crime;
import com.android.test.officeCrimesHolder.fragment.CrimeFragment;
import com.android.test.officeCrimesHolder.fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
        //return R.layout.activity_twopane;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.detailedFragmentContainer) == null) {
            Intent intent = new Intent(this, CrimeActivity.class);
            intent.putExtra(CrimeFragment.CRIME_ID, crime.getId());
            startActivity(intent);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment oldDetail = fragmentManager.findFragmentById(R.id.detailedFragmentContainer);
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            if(oldDetail != null) {
                fragmentTransaction.remove(oldDetail);
            }
            fragmentTransaction.add(R.id.detailedFragmentContainer, newDetail);
            fragmentTransaction.commit();
        }
    }
}
