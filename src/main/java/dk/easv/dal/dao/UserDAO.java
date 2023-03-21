package dk.easv.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UserDAO {

    private static ConnectionManager cm;


    public UserDAO() {
        cm = new ConnectionManager();
    }

    public static <User> boolean checkUserLog(String username, String password) throws SQLServerException {
        boolean userExists = false;
        try (Connection connection = cm.getConnection()) {
            String sql = "SET ANSI_WARNINGS OFF select * from dbo.[User] WHERE (username='"+ username +"' AND password='"+ password +"') SET ANSI_WARNINGS ON;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                userExists = true;
                String dbId = resultSet.getString("id");
                String dbUsername = resultSet.getString("username");
                String dbPassword = resultSet.getString("password");
                String dbEmail = resultSet.getString("email");
                String dbRole = resultSet.getString("role");
                System.out.println(dbId + " " + dbUsername + " " + dbPassword + " " + dbEmail + " " + dbRole);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userExists;
    }
}
