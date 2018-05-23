package test;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import za.ac.up.model.Delimiter;
import za.ac.up.model.Experiment;
import za.ac.up.model.ResultValue;
import za.ac.up.model.StoreRequest;
import za.ac.up.services.ExecutionFacade;
import za.ac.up.services.ReportGenerator;
import za.ac.up.services.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;

public class generateTest {

    @Test
    public void test() throws Exception
    {
//        StoreRequest request = new StoreRequest();
//        request.setResult(new ArrayList<>());
//        ExecutionFacade executionFacade = new ExecutionFacade();
//        for(int i = 50; i < 100; i++) {
//            Experiment experiment = new Experiment("" + i, "test user");
//            experiment.getResult().add(executionFacade.getResults(ExecutionFacade.Measurement.CPU));
//            experiment.getResult().add(executionFacade.getResults(ExecutionFacade.Measurement.MEMORY));
//            request.getResult().add(experiment);
//        }
//
//        System.out.print(MAPPER.writeValueAsString(request));
    }
}
