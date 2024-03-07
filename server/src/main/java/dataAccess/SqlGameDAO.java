package dataAccess;

import model.GameData;

import java.util.Map;

public class SqlGameDAO implements GameDAO {
    @Override
    public int createGame(String gameName, String authToken) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Map<Integer, GameData> listGames() {
        return null;
    }

    @Override
    public void updateGame(int gameID, GameData newGame) {

    }

    @Override
    public void clear() {

    }
}
