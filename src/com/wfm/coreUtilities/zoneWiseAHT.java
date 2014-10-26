package com.wfm.coreUtilities;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created by vriti on 10/12/14.
 * Logic by jyotibrb on 10/12/14.
 */
public class zoneWiseAHT {
    private final Map<Integer, AHTAtomic> idealAHTMap;
    public zoneWiseAHT(Map<Integer, AHTAtomic> idealAHTMap) {
        this.idealAHTMap = idealAHTMap;
        Map<Integer, Map<Integer, Double>> calculateAHTMap;

    }

    private double ahtOfRange(int start,int end){
        int sum = 0 , size = 0;
        int end1,end2;
        if (end < start){
           end1 = idealAHTMap.size();
           end2 = end;
        }else{
            end1 = end;
            end2 = 0;
        }
        for(int i = start;i <= end1 ; i++){
            AHTAtomic currentAHTAtomic = this.idealAHTMap.get(i);
            sum += currentAHTAtomic.getAverage() * currentAHTAtomic.getNoOfCalls();
            size += currentAHTAtomic.getNoOfCalls();
        }
        for(int i = 0;i <= end2 ; i++){
            if(end2>0){
            AHTAtomic currentAHTAtomic = this.idealAHTMap.get(i);
            sum += currentAHTAtomic.getAverage() * currentAHTAtomic.getNoOfCalls();
            size += currentAHTAtomic.getNoOfCalls();
            }
        }
        return sum/size;
    }

    private List<Double> listOfAHT(int start, int offset) {
        //todo : name change of variable listOfWeights
        List<Double> listOfWeights = Lists.newArrayList();
        for(int i=start;i<=idealAHTMap.size();i=i+offset) {
            int end = (i + offset - 1)%idealAHTMap.size();
            double ahtOfrange = ahtOfRange(i, end);
            listOfWeights.add(ahtOfrange);
        }
        return listOfWeights;
    }

    private double weightCalculator(List<Double> ahtList) {
        double min = getMinimumAHTOfRange(ahtList);
        double leastMeanSquareSum = 0;
        Iterator<Double> itr = ahtList.listIterator();
        while(itr.hasNext()){
            double val = itr.next();
            double difference = val-min;
            leastMeanSquareSum += difference*difference;
        }
        return leastMeanSquareSum;
    }

    private double getMinimumAHTOfRange(List<Double> ahtList) {
        double min = ahtList.get(0);
        Iterator<Double> itr = ahtList.listIterator();
        while(itr.hasNext()) {
            if(itr.next() < min ){
                min = itr.next();
            }
        }
        return min;
    }

    private Map<Integer, AHTAtomic> getZonalAHTFromIdealAHT(Map<Integer, AHTAtomic> idealAHTMap) {
        List<Double> zoneWiseFinalList = new ArrayList<Double>();
        int startFinal = 0,i;
        int offset = 12;
        double maxLeastMeanSq = 0;
        for(i=1;i<=offset;i++){
           double leastMeanSq = weightCalculator(listOfAHT(i,12));
           if(maxLeastMeanSq > leastMeanSq){
               zoneWiseFinalList = listOfAHT(i, offset);
               startFinal = i;
               maxLeastMeanSq = leastMeanSq;
           }
        }
        Map<Integer, AHTAtomic> zoneWiseAHTMap = new HashMap<Integer, AHTAtomic>();
        int end = startFinal + offset - 1;
        i = startFinal;
        Iterator<Double> itr = zoneWiseFinalList.listIterator();
        double nodeData = itr.next();
        while(i!=startFinal){
            if(i == end){
                end = end + offset - 1;
                nodeData = itr.next();
            }
            AHTAtomic newNode = new AHTAtomic();
            newNode.setAverage(nodeData);
            newNode.setHour(i);
            zoneWiseAHTMap.put(i,newNode);
            i = i++;
            i = i % idealAHTMap.size();
        }
        return zoneWiseAHTMap;
    }
}
