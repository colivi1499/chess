package handlers;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.SqlUserDAO;
import error.ErrorMessage;
import request.JoinGameRequest;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class JoinGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Deserialize request body into a request (not for clear)
        var serializer = new Gson();
        JoinGameRequest req = serializer.fromJson(request.body(), JoinGameRequest.class);
        String authToken = request.headers("authorization");

        //Call the correct service
        UserService userService = new UserService(new SqlUserDAO());
        ChessGame.TeamColor color;
        if (Objects.equals(req.playerColor(), "WHITE")) {
            color = ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(req.playerColor(), "BLACK")) {
            color = ChessGame.TeamColor.BLACK;
        } else {
            color = null;
            //response.status(200);
            //return serializer.toJson(new Object());
        }
        //Analyze the result from the service and set the correct status code
        try {
            userService.joinGame(color,req.gameID(),authToken, userService.getUsername(authToken));
        } catch (Exception e) {
            if (e.getMessage().endsWith("gameID")) {
                response.status(400);
                return serializer.toJson(new ErrorMessage("Error: bad request"));
            } else if (e.getMessage().endsWith("color")) {
                response.status(403);
                return serializer.toJson(new ErrorMessage("Error: already taken"));
            } else {
                    response.status(401);
                    return serializer.toJson(new ErrorMessage("Error: unauthorized"));
            }

        }
        response.status(200);
        //Return the deserialized result object
        return serializer.toJson(new Object());
    }
}
