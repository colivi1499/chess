package handlers;

import chess.ChessGame;
import com.google.gson.Gson;
import error.ErrorMessage;
import request.JoinGameRequest;
import result.ListGamesResult;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        String authToken = serializer.fromJson(request.headers("authorization: "), String.class);

        //Call the correct service
        GameService gameService = new GameService();
        ListGamesResult result = new ListGamesResult(gameService.listGames());
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(new Object());
    }
}
