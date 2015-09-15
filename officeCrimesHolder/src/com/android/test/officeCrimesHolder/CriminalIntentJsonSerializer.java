package com.android.test.officeCrimesHolder;


import android.content.Context;
import com.android.test.officeCrimesHolder.domain.Crime;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;

public class CriminalIntentJsonSerializer {
    private Context context;
    private String fileName;

    public CriminalIntentJsonSerializer(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws IOException, JSONException {
        JSONArray array = new JSONArray();
        for(Crime crime: crimes) {
            array.put(crime.toJSON());
        }
        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());

        } finally {
            if(writer != null) writer.close();
        }




    }
}
