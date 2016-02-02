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

public class AngleCalculator {

    public AngleCalculator(){}

    public double calculateAngle(Point currentPos, Point destination) {
        double angleGap = currentPos.getAngleGap();
        double angle, angleCorrection;
        angle = 360-(bearing(currentPos.getLat(),currentPos.getLng(), destination.getLat(), destination.getLng()));
        angleCorrection = angle + angleGap;
        return angleCorrection;
    }

    private double bearing(double lat1, double lng1, double lat2, double lng2){
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double longDiff= Math.toRadians(lng2-lng1);
        double y= Math.sin(longDiff)*Math.cos(latitude2);
        double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
       // Log.e("ANGLE", ""+ (Math.toDegrees(Math.atan2(y, x)) + 360) % 360);
        return (Math.toDegrees(Math.atan2(y, x))+360)%360;
    }
}