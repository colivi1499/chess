package handlers;

import chess.ChessGame;
import chess.PawnMovesCalculator;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.SqlAuthDAO;
import dataAccess.SqlGameDAO;
import error.ErrorMessage;
import org.eclipse.jetty.server.Authentication;
import request.JoinGameRequest;
import result.ListGamesResult;
import service.AuthService;
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
