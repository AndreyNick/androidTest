package com.android.test.officeCrimesHolder;


import android.content.Context;
import com.android.test.officeCrimesHolder.domain.Crime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

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

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();
            for (int i = 0; i < array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            //it happens on first start
            //TODO fix it
        } finally {
            if(reader != null){
                reader.close();
            }
        }
        return  crimes;
    }
}
