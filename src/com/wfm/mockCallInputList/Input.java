package com.wfm.mockCallInputList;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.Set;

import org.joda.time.DateTime;

/**
 * Created by jyotibrb on 10/5/14.
 */
public class Input {

    public Map<DateTime, DateTime> getInputSet() {
        return inputSet;
    }

    public void setInputSet(Map<DateTime, DateTime> inputSet) {
        this.inputSet = inputSet;
    }

    Map<DateTime, DateTime> inputSet;

    public Input(Map<Timestamp, Timestamp> test) {
        Map<DateTime, DateTime> newImprovedInput = new HashMap<DateTime, DateTime>();
        for(Timestamp t : test.keySet()) {
            newImprovedInput.put(new DateTime(t), new DateTime(test.get(t)));
        }
        inputSet = newImprovedInput;
    }

}
