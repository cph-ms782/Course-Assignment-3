package rest;

import facades.ApiFacade;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.ExecutionException;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author lam@cphbusiness.dk
 */
@Path("sw")
public class StarWarsResource {
    
    ApiFacade facade = ApiFacade.getApiFacade();

//    ApiFacade facade = ApiFacade.getApiFacade(EMF);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello star wars\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("data")
    @RolesAllowed({"user", "admin"})
    public String getData() throws IOException, ProtocolException, ExecutionException, InterruptedException {
//        String person = getSwappi("person/1");
        String data = facade.getAllDataInParallelWithQueue();
        return data;
    }

}
