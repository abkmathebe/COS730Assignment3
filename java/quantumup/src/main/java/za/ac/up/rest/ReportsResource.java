package za.ac.up.rest;

import za.ac.up.model.*;
import za.ac.up.services.ExecutionFacade;
import za.ac.up.services.ReportService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/reports")
public class ReportsResource {

    @EJB
    private ReportService reportService;

    @EJB
    private ExecutionFacade executionFacade;

    @GET
    @Path("/data")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getExperiment(@QueryParam("experimentId") String experimentId, @DefaultValue("COMMA") @QueryParam("delimiter") String delimiter) {
        if (delimiter == null || Delimiter.fromString(delimiter.toUpperCase()) == null) {
            return Response.status(415).entity(delimiter + " delimiter separated not supported").build();
        }
        ReportFile report = reportService.textReport(experimentId, Delimiter.valueOf(delimiter.toUpperCase()));
        if (report != null) {
            return Response.status(200).entity(report).build();
        } else {
            return Response.status(400).entity("No measurements for metric").build();
        }
    }

    @GET
    @Path("/generate")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getReport(@QueryParam("experimentId") String experimentId, @QueryParam("charttype") String chartType) {
        if (chartType == null || ChartTypes.fromString(chartType.toUpperCase()) == null) {
            return Response.status(415).entity(chartType + " chart type not supported").build();
        }

        List<ReportFile> report = reportService.getReport(experimentId, ChartTypes.fromString(chartType.toUpperCase()));
        if (report != null) {
            return Response.status(200).entity(report).build();
        } else {
            return Response.status(400).entity("No measurements for metric").build();
        }
    }

    @POST
    @Path("/store")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response storeReport(StoreRequest experiment) {
        StoreResponse response;
        try
        {
            response = executionFacade.storeReports(experiment);
            return Response.status(200).entity(response).build();
        }catch (Exception e)
        {
            return Response.status(400).entity(new StoreResponse(Boolean.FALSE, e.getMessage())).build();
        }
    }
}
