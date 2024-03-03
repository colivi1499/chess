package service;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

import javax.xml.crypto.Data;

public class UserService {
    MemoryUserDAO userDAO;
    AuthService authService;
    GameService gameService;
    public UserService(MemoryUserDAO userDAO, AuthService authService, GameService gameService) {
        this.userDAO = userDAO;
        this.authService = authService;
        this.gameService = gameService;
    }
    public AuthData register(UserData user) {
        try {
            userDAO.createUser(user);
            return authService.createAuth(user.username());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public AuthData login(UserData user) {
        try {
            userDAO.getUser(user.username());
            return authService.createAuth(user.username());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void logout(UserData user) throws DataAccessException {
        userDAO.getUser(user.username());
        authService.deleteAuth(authService.authDAO.getAuthFromUsername(user.username()).authToken());
    }

    public void joinGame(ChessGame.TeamColor clientColor, int gameID, String authToken, String username) {
        if (authService.authDAO.getAuth(authToken) != null) {
            if (clientColor == ChessGame.TeamColor.WHITE) {
                if (gameService.getGame(gameID).whiteUsername() == null)
                    gameService.updateGame(gameID,new GameData(gameID, username,gameService.getGame(gameID).blackUsername(),gameService.getGame(gameID).gameName(),gameService.getGame(gameID).game()));
            }
            if (clientColor == ChessGame.TeamColor.BLACK) {
                if (gameService.getGame(gameID).blackUsername() == null)
                    gameService.updateGame(gameID,new GameData(gameID, gameService.getGame(gameID).whiteUsername(),username,gameService.getGame(gameID).gameName(),gameService.getGame(gameID).game()));
            }
        }
    }

    public void clear() {
        userDAO.clear();
        authService.clear();
        gameService.clear();
    }

}
