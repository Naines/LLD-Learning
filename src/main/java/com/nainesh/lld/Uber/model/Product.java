package com.nainesh.lld.Uber.model;

public class Product {
    private String carName;
    private int perMinRate;
    private int perKmrate;

    public Product(String carName, int perMinRate, int perKmrate) {
        this.carName = carName;
        this.perMinRate = perMinRate;
        this.perKmrate = perKmrate;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getPerMinRate() {
        return perMinRate;
    }

    public void setPerMinRate(int perMinRate) {
        this.perMinRate = perMinRate;
    }

    public int getPerKmrate() {
        return perKmrate;
    }

    public void setPerKmrate(int perKmrate) {
        this.perKmrate = perKmrate;
    }
}
