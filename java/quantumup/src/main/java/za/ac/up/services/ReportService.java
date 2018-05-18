package za.ac.up.services;

import za.ac.up.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import za.ac.up.model.Report_;

@Stateless
public class ReportService {

    @EJB
    private ExecutionFacade executionFacade;

    @EJB
    private ReportGenerator reportGenerator;

    @PersistenceContext(unitName = "reports")
    private EntityManager em;

    private static final String IMAGE_QUERY = "SELECT * FROM report WHERE experimentId= #experimentId";

    public ByteArrayOutputStream getReport(String username, String experimentId, String metric, String chartType) {
        Boolean overwrite = Boolean.FALSE;

        Report report = getReportByExperimentId(experimentId);
        if (report != null) {
            for(Image image :report.getCharts())
            {
                if(image.getChartTypes().equals(ChartTypes.valueOf(chartType)) && image.getMetric().equals(Metric.valueOf(metric)))
                {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    try {
                        out.write(image.getImageAsBytea());
                        return out;
                    } catch (IOException e) {
                        //Regenerate
                    }
                }
            }
        }

        Experiment experiment = executionFacade.getExperiment(experimentId, username);
        ByteArrayOutputStream reportDoc = reportGenerator.generateReport(experiment, metric, ChartTypes.valueOf(chartType));
        if (reportDoc != null) {
            if (report != null) {
                Image img = new Image(metric, chartType);
                img.setReport(report);
                img.setImageAsBytea(reportDoc.toByteArray());
                report.getCharts().add(img);
                em.merge(report);
            } else {
                report = new Report();
                report.setExperimentId(experimentId);
                Image img = new Image(metric, chartType);
                img.setReport(report);
                img.setImageAsBytea(reportDoc.toByteArray());
                report.getCharts().add(img);
                em.persist(report);
            }
        }

        return reportDoc;
    }

    public ByteArrayOutputStream textReport(String username, String experimentId, String metric, Delimiter delimiter)
    {
        StringBuilder metricSb = new StringBuilder(metric);
        metricSb.append("-USAGE");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Experiment experiment = executionFacade.getExperiment(experimentId, username);
        StringBuilder sb = new StringBuilder("");

        sb.append("User");
        sb.append(delimiter.getDelimiter());
        sb.append(experiment.getDispatcher());
        sb.append("\n");
        sb.append("Experiment");
        sb.append(experiment.getTaskId());
        sb.append(delimiter.getDelimiter());
        sb.append("\n");

        for(Result result :experiment.getResult())
        {
            if(metricSb.toString().equalsIgnoreCase(result.getMeasurement()))
            {
                sb.append("User");
                sb.append(delimiter.getDelimiter());
                sb.append(experiment.getDispatcher());
                sb.append("\n");
                sb.append("Experiment");
                sb.append(experiment.getTaskId());
                sb.append(delimiter.getDelimiter());
                sb.append("\n");
                sb.append("Measurement");
                sb.append(delimiter.getDelimiter());
                sb.append(result.getMeasurement());
                sb.append("\n");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
                for(Values values :result.getValues())
                {
                    sb.append(sdf.format(values.getTimestamp()));
                    sb.append(delimiter.getDelimiter());
                    sb.append(values.getValue());
                    sb.append("\n");
                }


                try
                {
                    outputStream.write(sb.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }

                return outputStream;
            }
        }

        return null;
    }

    public void setExecutionFacade(ExecutionFacade executionFacade) {
        this.executionFacade = executionFacade;
    }

    public void setReportGenerator(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    private Report getReportByExperimentId(String experimentId)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Report.class);
        Root<Report> root = cq.from(Report.class);

        cq.where(cb.equal(root.get(Report_.experimentId), experimentId));
        cq.select(root);

        Report currency = null;
        TypedQuery<Report> q = em.createQuery(cq).setMaxResults(1);

        try {
            currency = q.getSingleResult();
        } catch (NoResultException e) {
        }

        return currency;
    }

}
