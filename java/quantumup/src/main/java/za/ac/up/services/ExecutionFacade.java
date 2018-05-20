package za.ac.up.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import za.ac.up.model.Experiment;
import za.ac.up.model.Experiments;
import za.ac.up.model.Result;
import za.ac.up.model.Values;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Stateless
public class ExecutionFacade {

    @Resource(name = "java:/global/env/useMock")
    private Boolean useMock;

    @Resource(name = "java:/global/env/executionUrl")
    private String executionUrl;

    private ObjectMapper MAPPER = new ObjectMapper();

    public Experiment getExperiment(String experimentId) {
        Experiment experiment = null;
        if(useMock) {
            experiment = new Experiment(experimentId, "Mock User");
            experiment.getResult().add(getResults(Measurement.CPU));
            experiment.getResult().add(getResults(Measurement.MEMORY));
        }else
        {
            try {
                Experiments experiments = MAPPER.readValue("", Experiments.class);
                for (Experiment experiment1 : experiments.getResult()) {
                    if (experiment1.getTaskId().equals(experimentId)) {
                        experiment = experiment1;
                        break;
                    }
                }
            }catch (Exception e)
            {
                //Do nothing
            }
        }

        return experiment;
    }

    private enum Measurement {
        CPU, MEMORY
    }

    private Result getResults(Measurement measurement) {
        Result result;
        switch (measurement) {
            case CPU:
                result = new Result("CPU-Usage");
                break;
            case MEMORY:
                result = new Result("Memory-Usage");
                break;
            default:
                return null;
        }
        getRandomValues(result, 100);

        return result;
    }

    private void getRandomValues(Result result, int num) {
        Date date = new Date();
        Random generator = new Random();
        int max;
        if(result.getMeasurement().equals("CPU-Usage"))
            max = 100;
        else
            max = 100_000_000;
        while (result.getValues().size()<num)
        {
            int i = generator.nextInt(max) + 1;
            Values value = new Values(date, i);
            result.getValues().add(value);
            date = addMillisecond(date);
        }
    }

    private Date addMillisecond(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MILLISECOND, 1);  // number of days to add
        return c.getTime();
    }

}
