package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    public static Map<String,AuthData> authTable = new HashMap<>();
    @Override
    public void createAuth(AuthData auth) {
        try {
            for (AuthData authData : authTable.values()) {
                if (authData.username() == auth.username()) {
                    throw new DataAccessException("Username " + auth.username() + " already has an authToken");
                }
            }
            authTable.put(auth.authToken(), auth);
        } catch (DataAccessException e) {
            System.out.println(e);
        }
    }

    @Override
    public AuthData getAuth(String authToken) {
        try {
            if (!authTable.containsKey(authToken)) {
                throw new DataAccessException("Invalid authToken");
            }
            return authTable.get(authToken);
        } catch (DataAccessException e) {
            System.out.println(e);
        }
        return null;
    }

    public String getAuthToken(String username) {
        try {
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
        } catch (DataAccessException e) {
            System.out.println(e);
        }
        return null;
    }

    public AuthData getAuthFromUsername(String username) {
        try {
            for (AuthData authData : authTable.values()) {
                if (authData.username().equals(username)) {
                    return authData;
                }
            }
            throw new DataAccessException("Invalid username " + username);
        } catch (DataAccessException e) {
            System.out.println(e);
            return null;
        }
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
