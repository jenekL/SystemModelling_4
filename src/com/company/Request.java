package com.company;

public class Request{
    private String name;
    private double time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Request(String name, double time) {
        this.name = name;
        this.time = time;
    }



}
