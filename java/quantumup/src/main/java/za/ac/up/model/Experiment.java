package za.ac.up.model;

import java.util.ArrayList;
import java.util.List;

public class Experiment {

    public Experiment() {
    }

    public Experiment(String taskId, String dispatcher) {
        this.taskId = taskId;
        this.dispatcher = dispatcher;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public List<Result> getResults() {
        if(results == null)
            results = new ArrayList<>();
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    private String taskId;
    private String dispatcher;
    private List<Result> results;
}
