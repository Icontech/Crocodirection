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
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import com.dsandberg.crocodirection.graphics.Model3D;
import com.dsandberg.crocodirection.models.Model;
import com.dsandberg.crocodirection.parser.ObjParser;
import com.dsandberg.crocodirection.parser.ParseException;
import com.dsandberg.crocodirection.util.AssetsFileUtil;
import com.dsandberg.crocodirection.util.BaseFileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.Config;
import edu.dhbw.andar.exceptions.AndARException;
import edu.dhbw.andar.pub.CustomRenderer;
import java.io.*;
import java.lang.reflect.Type;

public class ScanMarkerActivity extends AndARActivity {
    private ARToolkit artoolkit;
    private Model model;
    private Model3D model3d;
    private Model3D arrowModel3d;
    private Model arrowModel;
    private ProgressDialog waitDialog;
    private Resources res;
    private SharedPreferences prefs;
    private CrocoApplication app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CrocoApplication) getApplicationContext();
        CustomRenderer renderer = new CustomRenderer();
        super.setNonARRenderer(renderer);
        prefs = this.getSharedPreferences(app.getPrefsName(), 0);
        artoolkit = super.getArtoolkit();
        waitDialog = ProgressDialog.show(this, "", "Looking for model", true);
        waitDialog.show();
        new ModelLoader().execute();
    }

    public void onPause(){
        super.onPause();
        waitDialog.dismiss();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("AndAR EXCEPTION", ex.getMessage());
        finish();
    }

    private class ModelLoader extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Point currentPosition = getPointFromPrefs(app.getCurrentPos());
            AngleCalculator angleCalculator = new AngleCalculator();
            Point dest = getPointFromPrefs(app.getChosenDest());
            currentPosition.setDestination(dest);
            double angleToDestination = angleCalculator.calculateAngle(currentPosition,dest);
            currentPosition.setAngleToDestination(angleToDestination);

            BaseFileUtil fileUtil;
            fileUtil = new AssetsFileUtil(getResources().getAssets());
            fileUtil.setBaseFolder("models/");

                ObjParser parser = new ObjParser(fileUtil);

                    if(fileUtil != null) {
                        BufferedReader fileReader1 = fileUtil.getReaderFromName(app.getModelFileName());
                        BufferedReader fileReader2 = fileUtil.getReaderFromName(app.getArrowModelFileName());
                        if(fileReader1 != null) {
                            try {
                                model = parser.parse("Model", fileReader1);
                                model.setxScale(5f);
                                model.setyScale(5f);
                                model.setzScale(5f);
                                model.setYrot(180f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            model3d = new Model3D(model);
                        }
                        if(fileReader2 != null) {
                            try {
                                arrowModel = parser.parse("Model", fileReader2);
                                arrowModel.setxScale(5f);
                                arrowModel.setyScale(5f);
                                arrowModel.setzScale(5f);
                                arrowModel.setYrot((float)currentPosition.getAngleToDestination());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            arrowModel3d = new Model3D(arrowModel);
                        }
                    }
                    if(Config.DEBUG)
                        Debug.stopMethodTracing();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            waitDialog.dismiss();
            try {
                if(model3d!=null)
                    artoolkit.registerARObject(model3d);
                if(arrowModel3d!=null)
                    artoolkit.registerARObject(arrowModel3d);
            } catch (AndARException e) {
                e.printStackTrace();
            }
            startPreview();
        }
    }

    private Point getPointFromPrefs(String pointName){
        String jsonString = prefs.getString(pointName,"");
        Type type = new TypeToken<Point>(){}.getType();
        Gson gson = new Gson();
        Point point = gson.fromJson(jsonString, type);
        return point;
    }
}