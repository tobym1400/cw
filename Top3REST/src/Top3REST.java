/**
 * Created by el20jr on 21/11/22.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/top3")
public class Top3REST {

    @GET
    @Path("/returntop3/{authorID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String addPlainText(@PathParam("authorID") String authorID) {
        String x = "Err";
        try {
            x = QueryDB.returnTop3(authorID,Top3RESTStartup.connection);
        } catch (Exception error) {
            error.printStackTrace();
        }
        return x;
    }
}
