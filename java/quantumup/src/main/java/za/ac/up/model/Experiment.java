package za.ac.up.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Experiment {

    public Experiment() {
    }

    public Experiment(String taskID, String dispatcher) {
        this.taskID = taskID;
        this.dispatcher = dispatcher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public List<Result> getResult() {
        if (result == null)
            result = new ArrayList<>();
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    @Id
    @GeneratedValue(generator = "experiment_sequence")
    @SequenceGenerator(name = "experiment_sequence", sequenceName = "experiment_sequence", allocationSize = 1)
    @Column
    private int id;
    @Column
    private String taskID;
    @Column
    private String dispatcher;
    @JoinTable(name = "experiment_result", joinColumns = @JoinColumn(name = "experiment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "result_id", referencedColumnName = "id"))
    @OneToMany(cascade=CascadeType.ALL)
    private List<Result> result;
}
