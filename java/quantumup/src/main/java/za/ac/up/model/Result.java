package za.ac.up.model;

import java.util.ArrayList;
import java.util.List;

public class Result {
    public Result(String measurement) {
        this.measurement = measurement;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public List<Values> getValues() {
        if(values == null)
            values = new ArrayList<>();
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    private String measurement;
    private List<Values> values;
}
