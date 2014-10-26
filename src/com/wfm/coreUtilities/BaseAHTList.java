package com.wfm.coreUtilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by jyotibrb on 10/12/14.
 * This class retrieves the AHT Map from the db and updates it with today's calculated AHT Map.
 */
public class BaseAHTList {
    private final Map<Integer, AHTAtomic> idealAHTMap  = getIdealAHTMap();
    private List<AHTAtomic> hourlyAHTList = new ArrayList<AHTAtomic>();

    public BaseAHTList(List<AHTAtomic> hourlyAHTList) {
        this.hourlyAHTList = hourlyAHTList;
    }

    public void updateAHTList(){
        //update improved with the passed
        Iterator<AHTAtomic> listItr = hourlyAHTList.listIterator();
        Map<Integer, AHTAtomic> updatedAHTMap = idealAHTMap;
        while(listItr.hasNext()){
            updateNode(listItr.next(), updatedAHTMap);
        }

        saveUpdatedNode(updatedAHTMap);

    }

    private void updateNode(AHTAtomic atomicNode, Map<Integer, AHTAtomic> updatedAHTMap) {
        AHTAtomic reformedAHTAtomic = new AHTAtomic();
        AHTAtomic toUpdateAtomicNode = updatedAHTMap.get(atomicNode.getHour());
        long newNoOfCall = atomicNode.getNoOfCalls() + toUpdateAtomicNode.getNoOfCalls();
        double newAverage = (atomicNode.getNoOfCalls()*atomicNode.getAverage() + toUpdateAtomicNode.getNoOfCalls()*toUpdateAtomicNode.getAverage())/newNoOfCall;

        reformedAHTAtomic.setHour(atomicNode.getHour());
        reformedAHTAtomic.setAverage(newAverage);
        reformedAHTAtomic.setNoOfCalls(newNoOfCall);

        updatedAHTMap.put(atomicNode.getHour(), reformedAHTAtomic);
    }

    private Map<Integer, AHTAtomic> getIdealAHTMap(){
        //DB query for getting AHTMap
        return new HashMap<Integer, AHTAtomic>();
    }

    private void saveUpdatedNode(Map<Integer, AHTAtomic> updatedAHTMap){
        //write this function to save it in DB
    }
}
