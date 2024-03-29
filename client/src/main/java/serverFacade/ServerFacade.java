package serverFacade;

import chess.ChessGame;
import chess.PawnMovesCalculator;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import result.CreateGameResult;
import result.ListGamesResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;


public class ServerFacade {

    private final int port;
    private final String serverUrl;

    public ServerFacade(int port) {
        this.port = port;
        this.serverUrl = "http://localhost:" + port;
    }

    public AuthData register(String username, String password, String email) throws DataAccessException {
        var path = "/user";
        try {
            return this.makeRequest("POST", path, new UserData(username, password, email), AuthData.class, null);
        } catch (DataAccessException e) {
            throw new DataAccessException("Username is already taken");
        }
    }

    public void clear() throws DataAccessException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    public AuthData login(String username, String password) throws DataAccessException {
        var path = "/session";
        try {
            return this.makeRequest("POST", path, new UserData(username, password, null), AuthData.class, null);
        } catch (DataAccessException e) {
            switch(e.getMessage()) {
                case "failure: 401":
                case "failure: 403":
                    throw new DataAccessException("Username or password is incorrect");
                default: throw new DataAccessException("Invalid login");
            }
        }
    }

    public void logout(String authToken) throws DataAccessException {
        var path = "/session";
        try {
            this.makeRequest("DELETE", path, null, null, authToken);
        } catch (DataAccessException e) {
            throw new DataAccessException("Unauthorized logout");
        }
    }

    public CreateGameResult createGame(String gameName, String authToken) throws DataAccessException {
        var path = "/game";
        try {
            return this.makeRequest("POST", path, new CreateGameRequest(gameName), CreateGameResult.class, authToken);
        } catch (DataAccessException e) {
            throw new DataAccessException("Unable to create game");
        }
    }

    public void joinGame(String playerColor, int gameId, String authToken) throws DataAccessException {
        var path = "/game";
        try {
            this.makeRequest("PUT", path, new JoinGameRequest(playerColor, gameId), null, authToken);
        } catch (DataAccessException e) {
            switch(e.getMessage()) {
                case "failure: 403":
                    throw new DataAccessException("There is already a player in that spot");
                default: throw new DataAccessException("Unable to join game");
            }
        }

    }

    public ListGamesResult listGames(String authToken) throws DataAccessException {
        var path = "/game";
        return this.makeRequest("GET", path, null, ListGamesResult.class, authToken);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws DataAccessException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (authToken != null) {
                http.setRequestProperty("authorization", authToken);
            } if (request != null) {
                writeBody(request,http);
            }
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, DataAccessException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new DataAccessException("failure: " + status);
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
