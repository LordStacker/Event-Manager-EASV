package dk.easv.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.dal.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public int createUser(User user){
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO dbo.[User]  " +
                            "(username, password, email, role ) " +
                            "OUTPUT inserted.id VALUES (?, ?, ?, ?)");
            ps.setString(1, user.userName());
            ps.setString(2, user.userPassword());
            ps.setString(3, user.userEmail());
            ps.setString(4, user.role().toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<User> usersPlanners(Roles roles){
        ObservableList<User> usersPlanners = FXCollections.observableArrayList();
        try (Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("" +
                    "select * from dbo.[User] WHERE " +
                    "(role= ?);");
            ps.setString(1, roles.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int dbId = rs.getInt("id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String dbEmail = rs.getString("email");
                String dbRole = rs.getString("role");
                User user = new User(Roles.valueOf(dbRole), dbId, dbUsername, dbPassword, dbEmail);
                usersPlanners.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersPlanners;
    }
}
