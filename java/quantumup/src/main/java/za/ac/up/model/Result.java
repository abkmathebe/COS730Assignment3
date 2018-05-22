package za.ac.up.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Result {
    public Result() {
    }

    public Result(String measurement) {
        this.measurement = measurement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public List<ResultValue> getValues() {
        if (values == null)
            values = new ArrayList<>();
        return values;
    }

    public void setValues(List<ResultValue> values) {
        this.values = values;
    }

    @Id
    @GeneratedValue(generator = "result_sequence")
    @SequenceGenerator(name = "result_sequence", sequenceName = "result_sequence", allocationSize = 1)
    @Column
    private int id;
    @Column
    private String measurement;
    @JoinTable(name = "result_value", joinColumns = @JoinColumn(name = "result_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "value_id", referencedColumnName = "id"))
    @OneToMany(cascade=CascadeType.ALL)
    private List<ResultValue> values;
}
