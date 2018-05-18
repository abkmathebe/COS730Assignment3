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

    public List<Result> getResult() {
        if(result == null)
            result = new ArrayList<>();
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    private String taskId;
    private String dispatcher;
    private List<Result> result;
}
