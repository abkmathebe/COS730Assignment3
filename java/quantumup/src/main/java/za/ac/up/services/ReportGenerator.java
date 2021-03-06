package za.ac.up.services;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import za.ac.up.model.*;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.MILLIS;

@Stateless
public class ReportGenerator {

    DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_TIME;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy HH:mm");

    public Map<String, ReportFile> generateReport(Experiment experiment, ChartTypes chartTypes) {
        Map parameters = new HashMap();
        parameters.put("ExperimentName", experiment.getTaskID());
        parameters.put("Username", experiment.getDispatcher());
        parameters.put("Date", sdf.format(new Date()));

        DataBeanList dataBeanList;
        Map<String, ReportFile> reportFiles = new HashMap<>();

        for (Result result : experiment.getResult()) {

            dataBeanList = new DataBeanList();
            List<DataBean> dataBeans = new ArrayList<>();
            int last = result.getValues().size() - 1;
            parameters.put("measurement", result.getMeasurement());
            dataBeans.addAll(dataBeanList.getDataBeanList(result.getValues()));
            parameters.put("runtime", result.getValues().get(0).getTimestamp().until(result.getValues().get(last).getTimestamp(), MILLIS) + "ms");
            if (!dataBeans.isEmpty()) {
                JRBeanCollectionDataSource beanColDataSource = new
                        JRBeanCollectionDataSource(dataBeans, true);
                reportFiles.put(result.getMeasurement(), generateChart(parameters, beanColDataSource, chartTypes));

            }

        }

        return reportFiles;
    }

    public ReportFile generateChart(Map parameters, JRBeanCollectionDataSource jrBeanCollectionDataSource, ChartTypes chartTypes) {

        InputStream sourceFile;
        switch (chartTypes) {
            case LINE:
                sourceFile = getClass().getClassLoader().getResourceAsStream("linechart.jasper");
                break;
            case BAR:
                sourceFile = getClass().getClassLoader().getResourceAsStream("barchart.jasper");
                break;
            case PIE:
                sourceFile = getClass().getClassLoader().getResourceAsStream("pie.jasper");
                break;
            case SCATTER:
                sourceFile = getClass().getClassLoader().getResourceAsStream("scatter.jasper");
                break;
            default:
                sourceFile = null;
        }

        String encodedFile;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFile, parameters, jrBeanCollectionDataSource);
            JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());

            BufferedImage rendered_image = null;
            rendered_image = (BufferedImage) printManager.printPageToImage(jasperPrint, 0, 1.6f);
            ImageIO.write(rendered_image, "png", out);

            encodedFile = Base64.getEncoder().encodeToString(out.toByteArray());

        } catch (Exception e) {
            encodedFile = null;
        } finally {
            try {
                sourceFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (encodedFile != null) {
            return new ReportFile(encodedFile);
        } else {
            return null;
        }
    }

    public class DataBeanList {
        public ArrayList<DataBean> getDataBeanList(List<ResultValue> resultValues) {
            ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();
            int i = 0;
            for (ResultValue resultValue : resultValues) {
                dataBeanList.add(produce(resultValue.getTimestamp(), resultValue.getValue(), i));
                i++;
            }
            return dataBeanList;
        }

        /*
         * This method returns a DataBean object, with subjectName ,
         * and marks set in it.
         */
        private DataBean produce(LocalTime timestamp, Double value, Integer scatterX) {
            DataBean dataBean = new DataBean();

            dataBean.setTimestamp(dtf.format(timestamp));
            dataBean.setValue(value);
            dataBean.setScatterTimestamp(scatterX);

            return dataBean;
        }
    }

    public class DataBean {
        private String timestamp;
        private Double value;
        private Integer scatterTimestamp;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Integer getScatterTimestamp() {
            return scatterTimestamp;
        }

        public void setScatterTimestamp(Integer scatterTimestamp) {
            this.scatterTimestamp = scatterTimestamp;
        }
    }
}
