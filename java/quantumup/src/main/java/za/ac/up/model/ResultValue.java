package za.ac.up.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ResultValue {
    public ResultValue() {
    }

    public ResultValue(Date timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Id
    @GeneratedValue(generator = "resultvalue_sequence")
    @SequenceGenerator(name = "resultvalue_sequence", sequenceName = "resultvalue_sequence", allocationSize = 1)
    @Column
    private int id;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column
    private double value;
}
