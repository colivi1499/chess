package handlers;

import chess.PawnMovesCalculator;
import com.google.gson.Gson;
import error.ErrorMessage;
import model.AuthData;
import model.UserData;
import request.CreateGameRequest;
import result.CreateGameResult;
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
        String authToken = request.headers("authorization");

        //Call the correct service
        if (req.gameName() == null) {
            response.status(400);
            return serializer.toJson(new ErrorMessage("Error: bad request"));
        }

        GameService gameService = new GameService();
        int createGameResult = 0;
        try {
            createGameResult = gameService.createGame(req.gameName(),authToken);
        } catch (Exception e) {
            response.status(401);
            return serializer.toJson(new ErrorMessage("Error: unauthorized"));
        }
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(new CreateGameResult(createGameResult));
    }
}
