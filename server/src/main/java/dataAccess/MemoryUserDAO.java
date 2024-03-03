package dataAccess;

import model.UserData;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {

    public Map<String, UserData> userTable = new HashMap<>();
    @Override
    public void createUser(UserData user) throws DataAccessException {
        try {
            if (userTable.containsKey(user.username())) {
                throw new DataAccessException("Username " + user.username() + " already exists");
            }
            userTable.put(user.username(), user);
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

    public void removeUser(String username) throws DataAccessException {
        try {
            if (!userTable.containsKey(username)) {
                throw new DataAccessException("Invalid username");
            }
        } catch (DataAccessException e) {
            System.out.println(e);
        }
        userTable.remove(username);
    }



    @Override
    public void clear() {
        userTable.clear();
    }
}
