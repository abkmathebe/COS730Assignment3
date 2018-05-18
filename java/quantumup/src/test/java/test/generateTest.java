package test;


import org.junit.Test;
import za.ac.up.model.Delimiter;
import za.ac.up.services.ExecutionFacade;
import za.ac.up.services.ReportGenerator;
import za.ac.up.services.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class generateTest {

    @Test
    public void test() throws Exception
    {
        Delimiter delimiter = Delimiter.COMMA;
        System.err.println("Delimiter asfasfkas;mf" + delimiter.getDelimiter());
    }
}
