package za.ac.up.rest;

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
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getExperiments(@PathParam("username") String username)
    {
        return null;
    }

    @GET
    @Path("/generate")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getReport( @DefaultValue("User") @PathParam("username") String username, @QueryParam("experimentId") String experimentId,
                               @QueryParam("metric") String metric, @QueryParam("charttype") String chartType)
    {
        ByteArrayOutputStream report =  reportService.getReport(username, experimentId, metric, chartType);
        if(report != null) {
            return Response.status(200).entity(report.toByteArray()).
                    header("Content-Disposition", "attachment; filename=" + "\"" + experimentId + ".png\"").build();
        }else
        {
            return Response.status(400).entity("No measurements for metric").build();
        }
    }
}
