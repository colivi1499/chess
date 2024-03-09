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

public class LoginHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        UserData user = serializer.fromJson(request.body(), UserData.class);

        //Call the correct service
        UserService userService = new UserService(new SqlUserDAO());
        AuthData registerResult = new AuthData("","");
        try {
            registerResult = userService.login(user);
        } catch (Exception e) {
            if (e.getMessage().endsWith("exists")) {
                response.status(403);
                return serializer.toJson(new ErrorMessage("Error: already taken"));
            }
            else if (e.getMessage().endsWith("authToken")) {
                response.status(500);
                return serializer.toJson(new ErrorMessage("Error: unauthorized"));
            }
            else if (e.getMessage().endsWith("password")) {
                response.status(401);
                return serializer.toJson(new ErrorMessage("Error: unauthorized"));
            }
            else if (e.getMessage().endsWith("username")) {
                response.status(401);
                return serializer.toJson(new ErrorMessage("Error: unauthorized"));
            } else {
                response.status(200);
                return serializer.toJson(new ErrorMessage("Error: description"));
            }
        }
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(registerResult);
    }
}
