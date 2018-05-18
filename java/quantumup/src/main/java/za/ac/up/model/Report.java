package za.ac.up.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    private String experimentId;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "report")
    private List<Image> charts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public List<Image> getCharts() {
        if(charts == null)
            charts = new ArrayList<>();
        return charts;
    }

    public void setCharts(List<Image> charts) {
        this.charts = charts;
    }
}
