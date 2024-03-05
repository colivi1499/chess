package handlers;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import request.CreateGameRequest;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        CreateGameRequest req = serializer.fromJson(request.body(), CreateGameRequest.class);
        String authToken = serializer.fromJson(request.headers("authorization: "), String.class);

        //Call the correct service
        GameService gameService = new GameService();
        int createGameResult = gameService.createGame(req.gameName(),authToken);
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(createGameResult);
    }
}
