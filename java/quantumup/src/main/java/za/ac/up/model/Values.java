package za.ac.up.model;

import java.util.Date;

public class Values {
    public Values() {
    }

    public Values(Date timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private Date timestamp;
    private double value;
}
