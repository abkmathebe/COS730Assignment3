package za.ac.up.model;

import java.util.Date;

public class Values {
    public Values() {
    }

    public Values(Date timestamp, int value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private Date timestamp;
    private int value;
}
