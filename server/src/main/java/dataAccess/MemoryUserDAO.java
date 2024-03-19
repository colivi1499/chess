package dataAccess;

import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {

    public static Map<String, UserData> userTable = new HashMap<>();
    @Override
    public void createUser(UserData user) throws DataAccessException {
            if (userTable.containsKey(user.username())) {
                throw new DataAccessException("Username " + user.username() + " already exists");
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(user.password());
            userTable.put(user.username(), new UserData(user.username(),hashedPassword,user.email()));
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
            if (!userTable.containsKey(username)) {
                throw new DataAccessException("Invalid username");
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
