package handlers;

import com.google.gson.Gson;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        UserData user = serializer.fromJson(String.valueOf(request), UserData.class);

        //Call the correct service
        UserService userService = new UserService();
        userService.register(user);
        //Analyze the result from the service and set the correct status code
        int statusCode = 200;
        //Return the deserialized result object
        return null;
    }
}
