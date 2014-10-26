package com.wfm.coreUtilities;

/**
 * Created by jyotibrb on 10/12/14.
 */
public class AHTAtomic {
    int hour;
    double average;
    long noOfCalls;

    public AHTAtomic(int hour, double average, long noOfCalls) {
        this.hour = hour;
        this.average = average;
        this.noOfCalls = noOfCalls;
    }

    public AHTAtomic() {
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public long getNoOfCalls() {
        return noOfCalls;
    }

    public void setNoOfCalls(long noOfCalls) {
        this.noOfCalls = noOfCalls;
    }
}
