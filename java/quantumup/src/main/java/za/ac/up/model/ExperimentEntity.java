package za.ac.up.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

public class ExperimentEntity {

    @Column
    private String taskId;
    @Column
    private String dispatcher;
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "experiment")
    private List<Result> result;
}
