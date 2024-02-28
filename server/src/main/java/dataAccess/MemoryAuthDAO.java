package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {

    Map<String,AuthData> authTable = new HashMap<>();
    @Override
    public void createAuth(AuthData auth) {
        try {
            if (authTable.containsKey(auth.authToken())) {
                throw new DataAccessException("Username already exists");
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

    @Override
    public void deleteAuth(String authToken) {
        try {
            if (!authTable.containsKey(authToken)) {
                throw new DataAccessException("Invalid authToken");
            }
            authTable.remove(authToken);
        } catch (DataAccessException e) {
            System.out.println(e);
        }
    }
}
