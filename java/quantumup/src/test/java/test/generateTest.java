package test;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import za.ac.up.model.Delimiter;
import za.ac.up.model.ResultValue;
import za.ac.up.services.ExecutionFacade;
import za.ac.up.services.ReportGenerator;
import za.ac.up.services.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

public class generateTest {

    @Test
    public void test() throws Exception
    {
//        ResultValue v = new ResultValue(LocalTime.now(), 2);
//        ObjectMapper mapper = new ObjectMapper();
//        System.err.println(mapper.writeValueAsString(v));
    }
}
