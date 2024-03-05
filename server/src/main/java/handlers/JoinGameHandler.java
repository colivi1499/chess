package handlers;

import chess.ChessGame;
import com.google.gson.Gson;
import error.ErrorMessage;
import request.JoinGameRequest;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        JoinGameRequest req = serializer.fromJson(request.body(), JoinGameRequest.class);
        String authToken = serializer.fromJson(request.headers("authorization: "), String.class);

        //Call the correct service
        UserService userService = new UserService();
        if (req.playerColor().equals("WHITE")) {
            userService.joinGame(ChessGame.TeamColor.WHITE,req.gameID(),authToken, userService.getUsername(authToken));
        } else if (req.playerColor().equals("BLACK")) {
            userService.joinGame(ChessGame.TeamColor.WHITE,req.gameID(),authToken, userService.getUsername(authToken));
        } else {
            response.status(400);
            return new ErrorMessage("Error: bad request");
        }
        //Analyze the result from the service and set the correct status code
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(new Object());
    }
}
