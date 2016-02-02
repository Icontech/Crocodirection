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
import android.app.Application;

public class CrocoApplication extends Application{
    private final String prefsName = "CrocoPrefsFile";
    private final String currentPos = "currentPosition";
    private final String chosenDest = "chosenDestination";
    private final String destList = "destinations";
    private final double invalidLat = 92;
    private final double invalidLng = 181;
    private final String arrowModelFileName ="arrow3.obj";
    private final String modelFileName = "simpleWani.obj";
    private final String destinationFileName = "destinations.txt";

    public String getDestinationFileName() {
        return destinationFileName;
    }

    public String getArrowModelFileName() {
        return arrowModelFileName;
    }

    public String getModelFileName() {
        return modelFileName;
    }

    public double getInvalidLat() {
        return invalidLat;
    }

    public double getInvalidLng() {
        return invalidLng;
    }

    public String getPrefsName() {
        return prefsName;
    }

    public String getCurrentPos() {
        return currentPos;
    }

    public String getChosenDest() {
        return chosenDest;
    }

    public String getDestList() {
        return destList;
    }
}
