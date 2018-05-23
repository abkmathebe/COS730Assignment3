package za.ac.up.services;

import org.jfree.data.time.Millisecond;
import za.ac.up.model.*;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MILLIS;

@Stateless
public class ExecutionFacade {

    @Resource(name = "java:/global/env/useMock")
    private Boolean useMock;

    @PersistenceContext(unitName = "reports")
    private EntityManager em;

    public Experiment getExperiment(String experimentId) {
        Experiment experiment = null;
        if(useMock) {
            experiment = new Experiment(experimentId, "Mock User");
            experiment.getResult().add(getResults(Measurement.CPU));
            experiment.getResult().add(getResults(Measurement.MEMORY));
        }else
        {
            experiment = getExperimentByExperimentId(experimentId);
        }

        return experiment;
    }

    public StoreResponse storeReports(StoreRequest storeRequest)
    {
        for (int i = 0; i < storeRequest.getResult().size(); i++){
            em.persist(storeRequest.getResult().get(i));
        }
        StoreResponse response = new StoreResponse();
        response.setSuccessful(Boolean.TRUE);

        return response;
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
        LocalTime date = LocalTime.now();
        int max;
        if(result.getMeasurement().contains("CPU") || result.getMeasurement().contains("cpu"))
            max = 100;
        else
            max = 100_000_000;
        while (result.getValues().size()<num)
        {
            Random r = new Random();
            double randomValue = 0 + (max - 0) * r.nextDouble();
            double roundOff = (double)Math.round(randomValue * 100.0) / 100.0;
            ResultValue resultValue = new ResultValue(date, roundOff);
            result.getValues().add(resultValue);
            date = date.plus(1, MILLIS);
        }
    }

    private Date addMillisecond(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MILLISECOND, 1);  // number of days to add
        return c.getTime();
    }

    private Experiment getExperimentByExperimentId(String experimentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Experiment.class);
        Root<Experiment> root = cq.from(Experiment.class);

        cq.where(cb.equal(root.get(Experiment_.taskId), experimentId));
        cq.select(root);

        Experiment currency = null;
        TypedQuery<Experiment> q = em.createQuery(cq).setMaxResults(1);

        try {
            currency = q.getSingleResult();
        } catch (NoResultException e) {
        }

        return currency;
    }

}
