package dk.easv.dal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

class ConnectionManagerTest {

    @DisplayName("Connection manager throws NullPointerException with null in constructor")
    @Test
    void init(){
        try {
            new ConnectionManager(null);
            fail("Exception expected for null");
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @DisplayName("Test if connection is open")
    @Test
    void getConnection() {
        ConnectionManager cm = new ConnectionManager();
        try (Connection con = cm.getConnection()){
            Assertions.assertFalse(con.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}