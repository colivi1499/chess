package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import error.ErrorMessage;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        String authToken = serializer.fromJson(request.headers("authorization: "), String.class);

        //Call the correct service
        UserService userService = new UserService();
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
