package dataAccess;

import com.google.gson.Gson;
import model.AuthData;

import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SqlAuthDAO implements AuthDAO {

    public SqlAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        var statement = "INSERT INTO authData (username, authToken) VALUES (?, ?)";
        var json = new Gson().toJson(auth);
        executeUpdate(statement, auth.username(), auth.authToken());
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        /*try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, json FROM pet WHERE id=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, id);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readPet(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }*/
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public String getUsername(String authToken) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getAuthToken(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuthFromUsername(String name) throws DataAccessException {
        return null;
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
            CREATE TABLE IF NOT EXISTS  authData (
              `username` VARCHAR(50) NOT NULL,
              `authToken` VARCHAR(256) PRIMARY KEY
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
}
