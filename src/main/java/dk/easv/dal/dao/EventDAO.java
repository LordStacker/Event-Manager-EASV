package dk.easv.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.be.Event;
import dk.easv.dal.ConnectionManager;

import java.sql.*;

public class EventDAO {
    private final ConnectionManager cm = new ConnectionManager();
    public int createEvent(Event event) {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Event  (event_name, event_description, event_start_date, " +
                    "event_end_date, event_location, event_guidance) OUTPUT inserted.id VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventNotes());
            ps.setDate(3, Date.valueOf(event.getEventStartDate()));
            ps.setDate(4, event.getEventEndDate() == null ? null : Date.valueOf(event.getEventEndDate()));
            ps.setString(5, event.getEventLocation());
            ps.setString(6, event.getEventGuidance());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
