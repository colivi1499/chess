package dataAccess;

import com.google.gson.Gson;
import model.AuthData;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
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
        executeUpdate(statement, auth.username(), auth.authToken());
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM authData WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        throw new DataAccessException("Invalid authToken");
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM authData WHERE authToken=?";
        getAuth(authToken);
        executeUpdate(statement, authToken);
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        String username;
        try {
            username = getAuth(authToken).username();
        } catch (DataAccessException e){
            throw new DataAccessException("Authtoken not found");
        }
        return username;
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE authData";
        executeUpdate(statement);
    }

    @Override
    public String getAuthToken(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM authData WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs).authToken();
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        throw new DataAccessException("Invalid username");
    }

    @Override
    public AuthData getAuthFromUsername(String name) throws DataAccessException {
        try {
            return new AuthData(name, getAuthToken(name));
        } catch (DataAccessException e) {
            throw new DataAccessException("Invalid username " + name);
        }
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        var auth = new AuthData(username, authToken);
        return auth;
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
