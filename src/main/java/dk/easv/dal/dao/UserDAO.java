package dk.easv.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.dal.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static ConnectionManager cm;


    public UserDAO() {
        cm = new ConnectionManager();
    }

    public static List<User> checkUserLog(String username, String password){
        List<User> users = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("" +
                    "select * from dbo.[User] WHERE " +
                    "(username= ? AND password= ?);");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int dbId = rs.getInt("id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String dbEmail = rs.getString("email");
                String dbRole = rs.getString("role");
                User user = new User(Roles.valueOf(dbRole), dbId, dbUsername, dbPassword, dbEmail);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
