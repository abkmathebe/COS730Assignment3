package za.ac.up.model;

import javax.persistence.*;

@Entity
public class Image {

    public Image(){}

    public Image(String metric, String chartType)
    {
        this.metric = metric;
        this.chartTypes = ChartTypes.valueOf(chartType);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @Enumerated(EnumType.STRING)
    private ChartTypes chartTypes;

    @Column
    private String  metric;

    @Column
    private byte[] imageAsBytea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Report report;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChartTypes getChartTypes() {
        return chartTypes;
    }

    public void setChartTypes(ChartTypes chartTypes) {
        this.chartTypes = chartTypes;
    }

    public byte[] getImageAsBytea() {
        return imageAsBytea;
    }

    public void setImageAsBytea(byte[] imageAsBytea) {
        this.imageAsBytea = imageAsBytea;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
