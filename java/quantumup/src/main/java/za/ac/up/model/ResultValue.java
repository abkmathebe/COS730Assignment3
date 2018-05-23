package za.ac.up.model;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;
import com.migesok.jaxb.adapter.javatime.LocalTimeXmlAdapter;

@Entity
public class ResultValue {
    public ResultValue() {
    }

    public ResultValue(LocalTime timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
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
    @XmlJavaTypeAdapter(LocalTimeXmlAdapter.class)
    private LocalTime timestamp;
    @Column
    private double value;
}
