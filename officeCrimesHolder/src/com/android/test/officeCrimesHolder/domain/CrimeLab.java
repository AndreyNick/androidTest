package com.android.test.officeCrimesHolder.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
    private Context appContext;
    private List<Crime> crimesList;

    private CrimeLab(Context appContext) {
        this.appContext = appContext;
        crimesList = new ArrayList<Crime>();
        setTestCrimes();
    }

    public static CrimeLab getInstance(Context context) {
        if(crimeLab == null){
            crimeLab = new CrimeLab(context.getApplicationContext());
        }
        return crimeLab;
    }

    public List<Crime> getAllCrimes() {
        return crimesList;
    }

    public Crime getCrime(UUID id){
        for(Crime crime : crimesList){
            if(crime.getId().equals(id)) return crime;
        }
        return null;
    }

    private void setTestCrimes() {
        crimesList.add(new Crime("Test crime #1", true));
        crimesList.add(new Crime("Test crime #2", false));
    }
}
