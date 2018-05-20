package za.ac.up.services;

import org.apache.commons.io.FileUtils;
import za.ac.up.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Stateless
public class ReportService {

    @EJB
    private ExecutionFacade executionFacade;

    @EJB
    private ReportGenerator reportGenerator;

    @PersistenceContext(unitName = "reports")
    private EntityManager em;

    public List<ReportFile> getReport(String experimentId, ChartTypes chartType) {

        List<ReportFile> encodedReports = new ArrayList<>();
        Report report = getReportByExperimentId(experimentId);
        if (report != null) {
            for (Image image : report.getCharts()) {
                if (image.getChartTypes().equals(chartType)) {
                    encodedReports.add(new ReportFile(Base64.getEncoder().encodeToString(image.getImageAsBytea())));
                }
            }
        } else {
            Experiment experiment = executionFacade.getExperiment(experimentId);
            Map<String, ReportFile> reportDocsMap = reportGenerator.generateReport(experiment, chartType);

            report = new Report();
            report.setExperimentId(experimentId);

            for (String measure : reportDocsMap.keySet()) {
                String encoded = reportDocsMap.get(measure).getEncodedFile();
                Image img = new Image(measure, chartType.name());
                img.setReport(report);
                img.setImageAsBytea(Base64.getDecoder().decode(encoded));
                img.setChartTypes(chartType);
                img.setMetric(measure);
                report.getCharts().add(img);

                encodedReports.add(new ReportFile(encoded));
            }
            em.persist(report);
        }

        return encodedReports;
    }

    public ReportFile textReport(String experimentId, Delimiter delimiter) {

        Experiment experiment = executionFacade.getExperiment(experimentId);

        if (experiment != null) {
            StringBuilder sb = new StringBuilder("");
            sb.append("User");
            sb.append(delimiter.getDelimiter());
            sb.append(experiment.getDispatcher());
            sb.append("\n");
            sb.append("Experiment");
            sb.append(delimiter.getDelimiter());
            sb.append(experiment.getTaskId());
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            for (Result result : experiment.getResult()) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
                sb.append("Measurement");
                sb.append(delimiter.getDelimiter());
                sb.append(result.getMeasurement());
                sb.append("\n");
                sb.append("\n");
                for (Values values : result.getValues()) {
                    sb.append(sdf.format(values.getTimestamp()));
                    sb.append(delimiter.getDelimiter());
                    sb.append(values.getValue());
                    sb.append("\n");
                }

                sb.append("\n");
                sb.append("\n");

            }

            File file = new File(experimentId + ".txt");
            try {
                FileUtils.writeStringToFile(file, sb.toString());
                return new ReportFile(Base64.getEncoder().encodeToString(loadFile(file)));
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }

    }

    private static byte[] loadFile(File file) throws IOException {
        byte[] bytes;
        try (InputStream is = new FileInputStream(file)) {

            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                // File is too large
            }
            bytes = new byte[(int) length];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }

        }
        return bytes;
    }

    private Report getReportByExperimentId(String experimentId) {
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
