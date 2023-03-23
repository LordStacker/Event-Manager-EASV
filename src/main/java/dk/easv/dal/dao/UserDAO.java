package dk.easv.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static ConnectionManager cm;


    public UserDAO() {
        cm = new ConnectionManager();
    }

    public static List<User> checkUserLog(String username, String password) throws SQLServerException {
        List<User> users = new ArrayList<>();
        try (Connection connection = cm.getConnection()) {
            String sql = "SET ANSI_WARNINGS OFF select * from dbo.[User] WHERE (username='"+ username +"' AND password='"+ password +"') SET ANSI_WARNINGS ON;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                String dbUsername = resultSet.getString("username");
                String dbPassword = resultSet.getString("password");
                String dbEmail = resultSet.getString("email");
                String dbRole = resultSet.getString("role");
                User user = new User(Roles.valueOf(dbRole), dbId, dbUsername, dbPassword, dbEmail);
                users.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}