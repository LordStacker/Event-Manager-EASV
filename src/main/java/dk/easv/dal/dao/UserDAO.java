package dk.easv.dal.dao;

import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.daoInterfaces.IUserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {

    private final ConnectionManager cm = new ConnectionManager();


    @Override
    public User checkUserLog(String username, String password){
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
                return new User(Roles.valueOf(dbRole), dbId, dbUsername, dbPassword, dbEmail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
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

    @Override
    public ObservableList<User> usersPlanners(Roles role){
        ObservableList<User> usersPlanners = FXCollections.observableArrayList();
        try (Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("" +
                    "select * from dbo.[User] WHERE " +
                    "(role= ?);");
            ps.setString(1, role.toString());
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

    @Override
    public int deleteUser(int id){
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.[User] WHERE id = ? ");
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
