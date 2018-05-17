package za.ac.up.services;

import za.ac.up.model.Experiment;
import za.ac.up.model.Result;
import za.ac.up.model.Values;

import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Stateless
public class ExecutionFacade {

    public Experiment getExperiment(String experimentId, String username) {
        Experiment experiment = new Experiment(experimentId, username);
        experiment.getResults().add(getResults(Measurement.CPU));
        experiment.getResults().add(getResults(Measurement.MEMORY));

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
