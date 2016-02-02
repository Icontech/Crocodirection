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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;

public class LocationAsyncTask extends AsyncTask<Void, Void, Void> implements LocationListener {
    private LocationManager locationManager;
    private String provider;
    private ProgressDialog dialog;
    private double lat;
    private double lng;
    private Activity activity;
    private CrocoApplication app;
    SharedPreferences prefs;

    public LocationAsyncTask(Activity act, CrocoApplication application){
        app = application;
        lat = app.getInvalidLat();
        lng = app.getInvalidLng();
        activity = act;
        locationManager = (LocationManager) activity.getSystemService(Activity.LOCATION_SERVICE);
        prefs = activity.getSharedPreferences(app.getPrefsName(), 0);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        SharedPreferences prefs = activity.getSharedPreferences(app.getPrefsName(),0);

        Looper.prepare();
        if (prefs.getString("currentProvider","").equals(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        else
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        Looper.loop();
        locationManager.removeUpdates(this);
        return null;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Searching for current position");
        dialog.show();
    }

    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(lat != app.getInvalidLat() && lng != app.getInvalidLng()) {
            Log.e("Provider: " + provider, "lat: " + lat + " lng: " + lng);
            Toast.makeText(activity,"Provider: "+provider+ ", lat: "+lat+" lng: "+lng, Toast.LENGTH_SHORT).show();
            Point currentPosition = new Point(lng,lat,app.getCurrentPos(),1);
            setCurrentPosition(currentPosition);
        }else{
            Toast.makeText(activity,"No coordinates found ",Toast.LENGTH_SHORT).show();
        }

    }

    public void setCurrentPosition(Point point){
        Gson gson = new Gson();
        String jsonPoint = gson.toJson(point);
        prefs.edit().putString(app.getCurrentPos(),jsonPoint).commit();
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        provider = location.getProvider();
        Looper.myLooper().quit();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}