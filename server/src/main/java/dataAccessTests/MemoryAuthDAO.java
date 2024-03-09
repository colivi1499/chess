package dataAccessTests;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    public static Map<String,AuthData> authTable = new HashMap<>();
    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
            authTable.put(auth.authToken(), auth);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
            if (!authTable.containsKey(authToken)) {
                throw new DataAccessException("Invalid authToken");
            }
            return authTable.get(authToken);
    }

    public String getAuthToken(String username) throws DataAccessException {
            boolean validUsername = false;
            String authToken = "";
            for (AuthData authData : authTable.values()) {
                if (authData.username().equals(username)) {
                    validUsername = true;
                    authToken = authData.authToken();
                }
                if (!validUsername) {
                    throw new DataAccessException("Invalid username");
                }
            }
            return authToken;
    }

    public String getUsername(String authToken) throws DataAccessException {
        if (!authTable.containsKey(authToken))
            throw new DataAccessException("Authtoken not found");
        return authTable.get(authToken).username();
    }

    public AuthData getAuthFromUsername(String username) throws DataAccessException {
            for (AuthData authData : authTable.values()) {
                if (authData.username().equals(username)) {
                    return authData;
                }
            }
            throw new DataAccessException("Invalid username " + username);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
            if (!authTable.containsKey(authToken)) {
                throw new DataAccessException("Invalid authToken");
            }
            authTable.remove(authToken);
    }

    public void clear() {
        authTable.clear();
    }
}
