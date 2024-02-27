package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> chessGames = new HashMap<>();

    @Override
    public void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException {
        try {
            if (chessGames.containsKey(gameID)) {
                throw new DataAccessException("gameID already taken");
            }
            chessGames.put(gameID, new GameData(gameID, whiteUsername, blackUsername, gameName, game));
        } catch (DataAccessException e) {
            System.out.println(e);
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try {
            if (!chessGames.containsKey(gameID)) {
                throw new DataAccessException("Invalid gameID");
            }
            return chessGames.get(gameID);
        } catch (DataAccessException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Map<Integer, GameData> listGames() {
        return chessGames;
    }

    @Override
    public void updateGame(ChessGame newGame) {

    }
}
