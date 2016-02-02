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

public class Point {
    private double lng;
    private double lat;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private Point destination;
    private double angleToDestination;
    private double angleGap; //TO BE IMPLEMENTED

    public Point(double lng, double lat, String name, int id){
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.id = id;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAngleToDestination() {
        return angleToDestination;
    }

    public void setAngleToDestination(double angleToDestination) {
        this.angleToDestination = angleToDestination;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    //TO DO
    public void setAngleGap(double angleGap) {
        this.angleGap = angleGap;
    }

    public double getAngleGap() {
        return angleGap;
    }
}