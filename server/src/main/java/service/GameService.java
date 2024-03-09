package service;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.GameData;
import model.UserData;
import result.GameResult;

import java.awt.color.ICC_ColorSpace;
import java.util.ArrayList;
import java.util.Collection;

public class GameService {
    static GameDAO gameDAO;

    public GameService() {
        this(new MemoryGameDAO());
    }

    public GameService(GameDAO gameDAO) {
        GameService.gameDAO = gameDAO;
    }

    public int createGame(String gameName, String authToken) throws DataAccessException {
        return gameDAO.createGame(gameName,authToken);
    }

    public Collection<GameResult> listGames() throws DataAccessException {
        Collection<GameResult> listGamesResult = new ArrayList<>();
        for (GameData gameData : gameDAO.listGames().values()) {
            listGamesResult.add(new GameResult(gameData.gameID(),gameData.whiteUsername(), gameData.blackUsername(),gameData.gameName()));
        }
        return listGamesResult;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return gameDAO.getGame(gameID);
    }

    public void updateGame(int gameID, GameData newGame) throws DataAccessException {
        gameDAO.updateGame(gameID,newGame);
    }

    public void clear() throws DataAccessException {
        gameDAO.clear();
    }

}
