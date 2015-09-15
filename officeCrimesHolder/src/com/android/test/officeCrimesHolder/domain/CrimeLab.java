package com.android.test.officeCrimesHolder.domain;

import android.content.Context;
import android.util.Log;
import com.android.test.officeCrimesHolder.CriminalIntentJsonSerializer;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";
    private static CrimeLab crimeLab;

    private CriminalIntentJsonSerializer serializer;
    private Context appContext;
    private ArrayList<Crime> crimesList;

    private CrimeLab() {
        //this.appContext = appContext;
        crimesList = new ArrayList<Crime>();
    }

    public static CrimeLab getInstance() {
        if(crimeLab == null){
            crimeLab = new CrimeLab();
        }
        return crimeLab;
    }

    public boolean saveCrimes() {
        try {
            serializer.saveCrimes(crimesList);
            Log.d(TAG, "crimes was saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error during save the crimes: ", e);
            return false;
        }
    }

    public ArrayList<Crime> getAllCrimes() {
        return crimesList;
    }

    public Crime getCrime(UUID id){
        for(Crime crime : crimesList){
            if(crime.getId().equals(id)) return crime;
        }
        return null;
    }

    public void addCrime(Crime crime) {
        crimesList.add(crime);
    }


}
