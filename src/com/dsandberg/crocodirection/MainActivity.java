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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ArrayAdapter<String> adapter;
    private SharedPreferences prefs;
    private ArrayList<Point> destinations;
    private AlertDialog alertDialog;
    private CrocoApplication app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CrocoApplication) getApplicationContext();
        setContentView(R.layout.main);
        TextView titleOneTxt = (TextView)findViewById(R.id.titleOneTxt);
        TextView titleTwoTxt = (TextView)findViewById(R.id.titleTwoTxt);
        Button destinationBtn = (Button)findViewById(R.id.destinationBtn);
        Button positionBtn = (Button)findViewById(R.id.positionBtn);
        Typeface allerBold = Typeface.createFromAsset(getAssets(), "fonts/aller_bd.ttf");
        Typeface allerNormal = Typeface.createFromAsset(getAssets(), "fonts/aller_lt.ttf");
        titleOneTxt.setTypeface(allerBold);
        titleTwoTxt.setTypeface(allerBold);
        destinationBtn.setTypeface(allerNormal);
        prefs = this.getSharedPreferences(app.getPrefsName(), 0);
        destinations = new ArrayList<Point>();
        new FileReaderAsyncTask(MainActivity.this, app, prefs).execute(app.getDestinationFileName());

    }

    public void chooseDestination(View view){
        destinations = getDestinations();
        String[] items = new String[destinations.size()];
        for (int i = 0; i<destinations.size(); i++){
            items[i]=destinations.get(i).getName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);


        if(prefs.getString(app.getCurrentPos(), null) != null) {
            alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Where do you want to go?")
                    .setAdapter(adapter, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Gson gson = new Gson();
                            String jsonDest = gson.toJson(destinations.get(which));
                            prefs.edit().putString(app.getChosenDest(), jsonDest).commit();
                            Intent intent = new Intent(MainActivity.this, ScanMarkerActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }).create();
            alertDialog.show();
        } else
            Toast.makeText(this,"Please set your current position first.",Toast.LENGTH_LONG).show();
    }

    private ArrayList<Point> getDestinations(){
        String jsonDest = prefs.getString(app.getDestList(),"");
        Type type = new TypeToken<List<Point>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<Point> destinationList = gson.fromJson(jsonDest, type);
        return destinationList;
    }

    public void getCurrentPosition(View view){
        String[] item = new String[] {"GPS", "Network"};
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, item);

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("GPS or Network?")
                .setAdapter(adapt, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0)
                            prefs.edit().putString("currentProvider", LocationManager.GPS_PROVIDER).apply();
                        else
                            prefs.edit().putString("currentProvider", LocationManager.NETWORK_PROVIDER).apply();
                        dialog.dismiss();
                        new LocationAsyncTask(MainActivity.this, app).execute();
                    }
                }).create();
        alertDialog.show();
    }

    public void onPause(){
        super.onPause();
        if(alertDialog != null)
            alertDialog.dismiss();
    }
}