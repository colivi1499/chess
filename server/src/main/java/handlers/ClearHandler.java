package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SqlUserDAO;
import error.ErrorMessage;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception, DataAccessException {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();

        //Call the correct service
        UserService userService = new UserService(new SqlUserDAO());
        userService.clear();
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(new Object());
    }
}
