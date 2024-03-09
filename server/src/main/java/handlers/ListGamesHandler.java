package handlers;

import com.google.gson.Gson;
import dataAccessTests.SqlAuthDAO;
import dataAccessTests.SqlGameDAO;
import error.ErrorMessage;
import result.ListGamesResult;
import serviceTests.AuthService;
import serviceTests.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        String authToken = request.headers("authorization");
        try {
            AuthService authService = new AuthService(new SqlAuthDAO());
            authService.authDAO.getAuth(authToken);
        } catch (Exception e) {
            response.status(401);
            return serializer.toJson(new ErrorMessage("Error: unauthorized"));
        }

        //Call the correct service
        GameService gameService = new GameService(new SqlGameDAO());
        ListGamesResult result = new ListGamesResult(gameService.listGames());
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(result);
    }
}
