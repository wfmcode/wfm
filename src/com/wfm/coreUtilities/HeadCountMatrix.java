package com.wfm.coreUtilities;

import org.apache.commons.math.linear.*;

import java.util.Map;

/**
 * Created by jyotibrb on 10/12/14.
 */
public class HeadCountMatrix {
    private RealMatrix headCountMatrix;

    public RealMatrix getHeadCountMatrix() {
        return headCountMatrix;
    }

    private HeadCountMatrix(RealMatrix hctMatrix) {
        this.headCountMatrix = hctMatrix;
    }

    public static HeadCountMatrix getHeadCountMatrixForCallCountMatrix(Map<Integer, AHTAtomic> zonalAHTMap, CallCountMatrix callCountMatrix) {
        RealMatrix callMatrix = callCountMatrix.getCallCountMatrix();
        RealMatrix headMatrix = new Array2DRowRealMatrix(callMatrix.getRowDimension(), callMatrix.getColumnDimension());
        double entry;
        for(int i=0;i<callMatrix.getRowDimension();i++) {
            for(int j=0;i<callMatrix.getColumnDimension();j++) {
                entry = callMatrix.getEntry(i,j)/zonalAHTMap.get(j).getAverage();
                long entryVal = Math.round(entry);
                headMatrix.setEntry(i, j, entryVal);
            }
        }
        return new HeadCountMatrix(headMatrix);
    }
}
