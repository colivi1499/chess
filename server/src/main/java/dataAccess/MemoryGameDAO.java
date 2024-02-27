package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public class MemoryGameDAO implements GameDAO {
    @Override
    public void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {

    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public Collection<ChessGame> listGames() {
        return null;
    }

    @Override
    public void updateGame(ChessGame newGame) {

    }
}
