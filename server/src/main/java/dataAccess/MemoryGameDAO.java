package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private static Map<Integer, GameData> chessGames = new HashMap<>();
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();

    @Override
    public int createGame(String gameName, String authToken) throws DataAccessException {
        int gameID = 0;
            if (authDAO.getAuth(authToken) != null) {
                gameID = generateGameID(gameName);
                chessGames.put(gameID, new GameData(gameID,null,null,gameName,new ChessGame()));
            }
        return gameID;
    }


    @Override
    public GameData getGame(int gameID) throws DataAccessException {
            if (!chessGames.containsKey(gameID)) {
                throw new DataAccessException("Invalid gameID");
            }
            return chessGames.get(gameID);
    }

    @Override
    public Map<Integer, GameData> listGames() {
        return chessGames;
    }

    @Override
    public void updateGame(int gameID, GameData newGame){
        try {
            if (!chessGames.containsKey(gameID)) {
                throw new DataAccessException("Invalid gameID");
            }
            chessGames.replace(gameID,newGame);
        } catch (DataAccessException e) {
            System.out.println(e);
        }
    }

    public void clear() {
        chessGames.clear();
    }

    private int generateGameID(String gameName) {
        String id = gameName + System.currentTimeMillis();
        return Math.abs(id.hashCode()) + 1;
    }

    public int getID(String gameName) {
        try {
            for (GameData gameData : chessGames.values()) {
                if (gameData.gameName().equals(gameName)) {
                    return gameData.gameID();
                }
            }
            throw new DataAccessException("Invalid gameName " + gameName);
        } catch (DataAccessException e) {
            System.out.println(e);
            return 0;
        }
    }
}
