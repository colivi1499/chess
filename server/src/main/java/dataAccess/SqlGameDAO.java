package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SqlGameDAO implements GameDAO {

    private static SqlAuthDAO sqlAuthDAO;
    public SqlGameDAO() throws DataAccessException {
        sqlAuthDAO = new SqlAuthDAO();
        configureDatabase();
    }

    @Override
    public int createGame(String gameName, String authToken) throws DataAccessException {
        var statement = "INSERT INTO games (gameID, gameData) VALUES (?, ?)";

        int gameID = 0;
        if (sqlAuthDAO.getAuth(authToken) != null) {
            gameID = generateGameID(gameName);
            var gameData = new Gson().toJson(new GameData(gameID,null,null,gameName,new ChessGame()));
            executeUpdate(statement, gameID, gameData);
        }
        return gameID;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameData FROM games WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        throw new DataAccessException("Invalid gameID");
    }

    @Override
    public Map<Integer, GameData> listGames() throws DataAccessException {
        var result = new HashMap<Integer, GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, gameData FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.put(readGame(rs).gameID(),readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    @Override
    public void updateGame(int gameID, GameData newGame) throws DataAccessException {
        var statement = "UPDATE games SET gameData= ? WHERE gameID = ?";
        if (getGame(gameID) != null) {
            var gameData = new Gson().toJson(newGame,GameData.class);
            executeUpdate(statement, gameData, gameID);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var json = rs.getString("gameData");
        var gameData = new Gson().fromJson(json, GameData.class);
        return gameData;
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `gameID` VARCHAR(50) NOT NULL PRIMARY KEY,
              `gameData` longtext NOT NULL
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    private int generateGameID(String gameName) {
        String id = gameName + System.currentTimeMillis();
        return Math.abs(id.hashCode()) + 1;
    }
}
