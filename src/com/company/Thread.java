package com.company;

public class Thread {
    private boolean free = true;
    private double time = 0;

    public void serviceRequest(Request request, double timeService){
        System.out.printf(request.getName() + " обслужится в %.2f", timeService);
        free = false;
        time = timeService;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
