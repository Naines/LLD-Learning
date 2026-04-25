package com.nainesh.lld.Uber.model;

public class Location {
    private double x;
    private double y;

    public Location(double lat, double lng) {
        this.x = lat;
        this.y = lng;
    }

    public double getLat() {
        return x;
    }

    public void setLat(double lat) {
        this.x = lat;
    }

    public double getLng() {
        return y;
    }

    public void setLng(double lng) {
        this.y = lng;
    }

    public double calDistance(Location a, Location b){
        return Math.sqrt(Math.pow((a.x-b.x),2)+Math.pow((a.y-b.y),2));
    }
}
