package com.wfm.coreUtilities;

import java.util.*;

import com.google.common.collect.Multimap;
import com.wfm.mockCallInputList.Input;
import org.joda.time.DateTime;

/**
 * Created by jyotibrb on 10/5/14.
 */

public class CallMapReformator {

    Map<Integer, Map<DateTime, DateTime>> hourlyMap;
    List<AHTAtomic> hourlyAHTList = new ArrayList<AHTAtomic>();
    int dayOfWeek;
    public Map<Integer, Map<DateTime, DateTime>> getHourlyMap() {
        return hourlyMap;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public CallMapReformator(Input input,int dayOfWeek) {
        Map<DateTime,DateTime> inputSet = input.getInputSet();
        this.dayOfWeek = dayOfWeek;
        FormatData(inputSet);
    }
    private void FormatData(Map<DateTime,DateTime> inputSet){

        for(DateTime t : inputSet.keySet()){
            int hourOfSet = t.getHourOfDay();
            int minOfSet = t.getMinuteOfHour();
            int hourlyMapKey = hourOfSet * 2;


            if(minOfSet >= 30){
                hourlyMapKey += 1;
            }

            if(hourlyMap.containsKey(hourlyMapKey)){
                hourlyMap.get(hourlyMapKey).put(t,inputSet.get(t));
            } else {
                Map<DateTime, DateTime> callData = new HashMap<DateTime, DateTime>();
                callData.put(t, inputSet.get(t));
                hourlyMap.put(hourlyMapKey, callData);
            }
        }

        populateHourlyAHTList();

    }

    private AHTAtomic getAHTByNode(int hourlyMapKey) {
        //iterate over the hourlyMapKey in hourlyMap and find average over all callData's
        Map<DateTime, DateTime> singleHourMap = hourlyMap.get(hourlyMapKey);
        Iterator<DateTime> i = singleHourMap.keySet().iterator();
        int sum = 0;
        while(i.hasNext()){
            DateTime key = i.next();
            DateTime value = singleHourMap.get(key);
            sum += (value.getMillis() - key.getMillis());
        }
        int sizeOfMap = singleHourMap.size();
        double avgAHT = sum/sizeOfMap;
        return new AHTAtomic(hourlyMapKey, avgAHT, sizeOfMap);
    }

    // 0-48 aht list
    public void populateHourlyAHTList() {
        for(Integer hourOfDay : hourlyMap.keySet()){
            hourlyAHTList.add(getAHTByNode(hourOfDay));
        }
    }

    public int getCallCountByNode(int hourlyMapKey){
        return hourlyMap.get(hourlyMapKey).size();
    }
}
