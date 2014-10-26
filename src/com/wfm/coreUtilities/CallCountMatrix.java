package com.wfm.coreUtilities;

import org.apache.commons.math.linear.RealMatrix;

/**
 * Created by vriti on 10/6/14.
 */
public class CallCountMatrix {
    RealMatrix callCtM;

    public RealMatrix getCallCountMatrix() {
        return callCtM;
    }

    public CallCountMatrix(int dayOfWeek, CallMapReformator oneDayData, RealMatrix headCtM) {
        this.callCtM = headCtM;
        for(int hourOfDay : oneDayData.getHourlyMap().keySet()){
            headCtM.setEntry(dayOfWeek, hourOfDay, oneDayData.getCallCountByNode(hourOfDay));
        }
    }
}
