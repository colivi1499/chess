package handlers;

import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)

        //Call the correct service

        //Analyze the result from the service and set the correct status code

        //Return the deserialized result object
        return null;
    }
}
