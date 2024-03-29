package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SqlUserDAO;
import error.ErrorMessage;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        String authToken = request.headers("authorization");

        //Call the correct service
        UserService userService = new UserService(new SqlUserDAO());
        try {
            userService.logout(authToken);
        } catch (DataAccessException e) {
            response.status(401);
            return serializer.toJson(new ErrorMessage("Error: unauthorized"));
        }
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(new Object());
    }
}
