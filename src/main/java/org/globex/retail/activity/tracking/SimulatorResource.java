package org.globex.retail.activity.tracking;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
@Path("/simulate")
public class SimulatorResource {

    @Inject
    SimulatorService simulatorService;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.TEXT_PLAIN })
    public Uni<Response> simulate(String payload) {
        JsonObject json = new JsonObject(payload);
        int count = json.getInteger("count");
        return simulatorService.simulate(count).onItem().transform(i ->
                Response.status(Response.Status.CREATED.getStatusCode()).entity("Simulated " + count + " activities").build());
    }

}
