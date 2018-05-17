package za.ac.up.services;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import za.ac.up.model.ChartTypes;
import za.ac.up.model.Experiment;
import za.ac.up.model.Result;
import za.ac.up.model.Values;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
public class ReportGenerator {

    //    @Inject
//    private DB db;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    public ByteArrayOutputStream generateReport(Experiment experiment, String metric, ChartTypes chartTypes) {
        Map parameters = new HashMap();
        parameters.put("ExperimentName", experiment.getTaskId());
        parameters.put("Username", experiment.getDispatcher());

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

        DataBeanList dataBeanList = new DataBeanList();
        List<DataBean> dataBeans = new ArrayList<>();
        for (Result result : experiment.getResults()) {

            if(result.getMeasurement().equals(metric)) {
                parameters.put("measurement", result.getMeasurement());
                parameters.put("startDate", sdf.format(result.getValues().get(0).getTimestamp()));
                parameters.put("endDate", sdf.format(result.getValues().get(result.getValues().size() - 1).getTimestamp()));
                dataBeans.addAll(dataBeanList.getDataBeanList(result.getValues()));
                break;
            }else
            {
                continue;
            }
        }

        if(!dataBeans.isEmpty()) {
            JRBeanCollectionDataSource beanColDataSource = new
                    JRBeanCollectionDataSource(dataBeans, true);

            return generateChart(parameters, beanColDataSource, sourceFile);
        }else
        {
            return null;
        }
    }

    public ByteArrayOutputStream generateChart(Map parameters, JRBeanCollectionDataSource jrBeanCollectionDataSource, InputStream sourceFile) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        OutputStream ouputStream = null;
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFile, parameters, jrBeanCollectionDataSource);
            DefaultJasperReportsContext.getInstance();
            JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());

            BufferedImage rendered_image = null;
            rendered_image = (BufferedImage) printManager.printPageToImage(jasperPrint, 0, 1.6f);
            ImageIO.write(rendered_image, "png", out);
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public class DataBeanList {
        public ArrayList<DataBean> getDataBeanList(List<Values> values) {
            ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();
            int i = 0;
            for (Values value : values) {
                dataBeanList.add(produce(value.getTimestamp(), value.getValue(), i));
                i++;
            }
            return dataBeanList;
        }

        /*
         * This method returns a DataBean object, with subjectName ,
         * and marks set in it.
         */
        private DataBean produce(Date timestamp, Integer value, Integer scatterX) {
            DataBean dataBean = new DataBean();

            dataBean.setTimestamp(sdf.format(timestamp));
            dataBean.setValue(value);
            dataBean.setScatterTimestamp(scatterX);

            return dataBean;
        }
    }

    public class DataBean {
        private String timestamp;
        private Integer value;
        private Integer scatterTimestamp;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
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
