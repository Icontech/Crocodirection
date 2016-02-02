/**
 Copyright (C) 2016  David Sandberg

 This file is part of Crocodirection.

 Crocodirection is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Crocodirection is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Crocodirection.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsandberg.crocodirection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;

public class FileReaderAsyncTask extends AsyncTask<String, Void, Void> {
    private Activity activity;
    private CrocoApplication app;
    private ProgressDialog dialog;
    private SharedPreferences prefs;

    public FileReaderAsyncTask(Activity activity, CrocoApplication application, SharedPreferences prefs){
        this.activity = activity;
        app = application;
        this.prefs = prefs;
    }

    @Override
    protected Void doInBackground(String... params) {
        String fileName = params[0];
        StringBuffer sb = new StringBuffer();
        InputStream inputStream = null;
        try {
            inputStream = app.getAssets().open(fileName);
            InputStreamReader inputreader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputreader);
            String line;
            ArrayList<Point> destinations = new ArrayList<Point>();
            int id = 1;
            while (( line = br.readLine()) != null) {
                String[] elements = line.split(",");
                Point p = new Point(Double.parseDouble(elements[0]),Double.parseDouble(elements[1]),elements[2], id);
                destinations.add(p);
                id++;
            }
            Gson gson = new Gson();
            String jsonDest = gson.toJson(destinations);
            prefs.edit().putString(app.getDestList(),jsonDest).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Loading destinations...");
        dialog.show();
    }

    protected void onPostExecute(Void result){
        if(dialog.isShowing())
            dialog.dismiss();
    }

}
