package dk.easv.gui.models;

import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.dal.ConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserModelTest {
    private final UserModel model = new UserModel();
    private final ConnectionManager cm = new ConnectionManager();
    private int id = 0;
    @BeforeEach
    void setUp() {
        try (Connection con = cm.getConnection()){
            ResultSet rs = con.prepareStatement("INSERT INTO dbo.[User] (username, password, email, role) OUTPUT inserted.id VALUES ('test', 'test', 'test@test.dk', 'EVENT_COORDINATOR')").executeQuery();
            rs.next();
            this.id = rs.getInt(1);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.[User] WHERE id = ? ");
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkUserLog() {
        User expected = new User(Roles.EVENT_COORDINATOR, id, "test", "test", "test@test.dk");
        User actual = model.checkUserLog("test", "test");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteUser(){
        int expected = 1;
        int actual = model.deleteUser(id);
        Assertions.assertEquals(expected, actual);
    }
}