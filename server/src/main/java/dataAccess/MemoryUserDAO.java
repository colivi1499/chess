package dataAccess;

import model.UserData;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {

    Map<String, UserData> userTable = new HashMap<>();
    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
        try {
            if (userTable.containsKey(username)) {
                throw new DataAccessException("Username already exists");
            }
            userTable.put(username, new UserData(username,password,email));
        } catch (DataAccessException e) {
            System.out.println(e);
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try {
            if (!userTable.containsKey(username)) {
                throw new DataAccessException("Invalid username");
            }
        } catch (DataAccessException e) {
            System.out.println(e);
        }
        return userTable.get(username);
    }

    @Override
    public void clear() {
        userTable.clear();
    }
}
