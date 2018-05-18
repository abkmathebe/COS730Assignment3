package za.ac.up.rest;

import za.ac.up.model.ChartTypes;
import za.ac.up.model.Delimiter;
import za.ac.up.model.Metric;
import za.ac.up.services.ReportService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;

@RequestScoped
@Path("/reports")
public class ReportsResource {

    @EJB
    private ReportService reportService;
    @GET
    @Path("/data")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getExperiment( @DefaultValue("User") @PathParam("username") String username, @QueryParam("experimentId") String experimentId,
                                   @QueryParam("metric") String metric, @DefaultValue("COMMA") @QueryParam("delimiter") String delimiter)
    {
        if(Delimiter.fromString(delimiter.toUpperCase()) == null)
        {
            return Response.status(415).entity(delimiter + " separated not supported").build();
        }
        if(Metric.fromString(metric.toUpperCase()) == null)
        {
            return Response.status(415).entity(metric + " not supported").build();
        }
        ByteArrayOutputStream report = reportService.textReport(username, experimentId, metric.toUpperCase(), Delimiter.valueOf(delimiter.toUpperCase()));
        if(report != null) {
            return Response.status(200).entity(report.toByteArray()).
                    header("Content-Disposition", "attachment; filename=" + "\"" + experimentId + metric + ".txt\"").build();
        }else
        {
            return Response.status(400).entity("No measurements for metric").build();
        }
    }

    @GET
    @Path("/generate")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getReport( @DefaultValue("User") @PathParam("username") String username, @QueryParam("experimentId") String experimentId,
                               @QueryParam("metric") String metric, @QueryParam("charttype") String chartType)
    {
        if(ChartTypes.fromString(chartType.toUpperCase()) == null)
        {
            return Response.status(415).entity(chartType + " not supported").build();
        }
        if(Metric.fromString(metric.toUpperCase()) == null)
        {
            return Response.status(415).entity(metric + " not supported").build();
        }

        ByteArrayOutputStream report =  reportService.getReport(username, experimentId, metric.toUpperCase(), chartType.toUpperCase());
        if(report != null) {
            return Response.status(200).entity(report.toByteArray()).
                    header("Content-Disposition", "attachment; filename=" + "\"" + experimentId + metric + ".png\"").build();
        }else
        {
            return Response.status(400).entity("No measurements for metric").build();
        }
    }
}
