package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.GameData;
import model.UserData;
import result.GameResult;

import java.awt.color.ICC_ColorSpace;
import java.util.ArrayList;
import java.util.Collection;

public class GameService {
    static MemoryGameDAO gameDAO;

    public GameService() {
        this(new MemoryGameDAO());
    }

    public GameService(MemoryGameDAO gameDAO) {
        GameService.gameDAO = gameDAO;
    }

    public int createGame(String gameName, String authToken) throws DataAccessException {
        return gameDAO.createGame(gameName,authToken);
    }

    public Collection<GameResult> listGames() {
        Collection<GameResult> listGamesResult = new ArrayList<>();
        for (GameData gameData : gameDAO.listGames().values()) {
            listGamesResult.add(new GameResult(gameData.gameID(),gameData.whiteUsername(), gameData.blackUsername(),gameData.gameName()));
        }
        return listGamesResult;
    }

    public GameData getGame(int gameID) {
        return gameDAO.getGame(gameID);
    }

    public void updateGame(int gameID, GameData newGame) {
        gameDAO.updateGame(gameID,newGame);
    }

    public void clear() {
        gameDAO.clear();
    }

}
