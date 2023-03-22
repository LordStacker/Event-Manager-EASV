package dk.easv.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.dal.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;

public class EventDAO {
    private ConnectionManager cm;

    public EventDAO(){
        cm = new ConnectionManager();
    }
    public void eventPostMethod(String eventName, String eventDescription, String eventLocation, LocalDate eventStartDate, LocalDate eventEndDate, String eventGuidance) throws SQLServerException {
        try(Connection connection = cm.getConnection()) {
            String sql = "SET ANSI_WARNINGS OFF INSERT INTO dbo.Event(event_name,\n" +
                    " event_description,\n" +
                    " event_location, \n" +
                    " event_start_date, \n" +
                    " event_end_date, \n" +
                    " event_guidance) VALUES ('"+ eventName +"','"+ eventDescription+"','"+eventLocation+"','"+ eventStartDate+"','"+ eventEndDate +"','"+ eventGuidance +"') SET ANSI_WARNINGS ON;";
            Statement statement = connection.createStatement();
            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();
                System.out.println("Inserted correctly");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
