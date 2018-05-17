package za.ac.up.services;

import za.ac.up.model.ChartTypes;
import za.ac.up.model.Experiment;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.ByteArrayOutputStream;

@Stateless
public class ReportService {

    @EJB
    private ExecutionFacade executionFacade;

    @EJB
    private ReportGenerator reportGenerator;

    public ByteArrayOutputStream getReport(String username, String experimentId, String metric, String chartType)
    {
        Experiment experiment = executionFacade.getExperiment(experimentId, username);
        return reportGenerator.generateReport(experiment, metric, ChartTypes.valueOf(chartType));
    }

    public void setExecutionFacade(ExecutionFacade executionFacade) {
        this.executionFacade = executionFacade;
    }

    public void setReportGenerator(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }
}
